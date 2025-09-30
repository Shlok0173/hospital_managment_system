package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.InsuranceResponseDto;
import com.example.HospitalManagmentSystemProject.dto.PatientEvent;
import com.example.HospitalManagmentSystemProject.dto.PatientRequestDto;
import com.example.HospitalManagmentSystemProject.dto.PatientResponseDto;
import com.example.HospitalManagmentSystemProject.entity.Patient;
import com.example.HospitalManagmentSystemProject.repository.PatientRepository;
import com.example.HospitalManagmentSystemProject.service.PatientEventMapperService;
import com.example.HospitalManagmentSystemProject.service.PatientService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImp implements PatientService {

    private Logger logger = (Logger) LoggerFactory.getLogger(PatientServiceImp.class);
    private final PatientRepository patientRepository;

    @Autowired
    private PatientNotificationListener emailService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientServiceImp(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    private PatientEventProducer patientEventProducer;

    @Autowired
    private PatientEventMapperService patientEventMapperService;
    @Override
    public PatientResponseDto registerPatient(PatientRequestDto patientRequestDto) {
        logger.info("Registering new patient with email: {}", patientRequestDto.getGmail());

        Patient patient = modelMapper.map(patientRequestDto, Patient.class);
        Patient savePatient = patientRepository.save(patient);

        logger.info("Patient registered successfully with ID: {}", savePatient.getId());

        PatientResponseDto responseDtos = modelMapper.map(savePatient, PatientResponseDto.class);

        if (savePatient.getInsurance() != null) {
            logger.debug("Mapping insurance details for PatientId={}", savePatient.getId());
            responseDtos.setInsurance(modelMapper.map(savePatient.getInsurance(), InsuranceResponseDto.class));
        }

        // ✅ Convert appointments list → IDs only
        if (savePatient.getAppointment() != null && !savePatient.getAppointment().isEmpty()) {
            logger.debug("PatientId={} has {} appointments. Mapping IDs only.",
                    savePatient.getId(), savePatient.getAppointment().size());
            responseDtos.setAppointmentIds(
                    savePatient.getAppointment().stream()
                            .map(app -> app.getId())
                            .collect(Collectors.toList())
            );
        } else {
            logger.debug("PatientId={} has no appointments.", savePatient.getId());
        }
        // --- publish kafka event (non-blocking) ---
        PatientEvent event = new PatientEvent();
        event.setId(savePatient.getId());
        event.setName(savePatient.getName());
        event.setGmail(savePatient.getGmail());
        event.setGender(savePatient.getGender());
        event.setBirthdate(savePatient.getBirthdate());
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setOccurredAt(java.time.Instant.now());

        patientEventProducer.publish(event);
        logger.info("Published PatientRegisteredEvent for id={}", savePatient.getId());

        // Map ResponseDto → Event
        PatientEvent patientEvent = patientEventMapperService.mapToPatientEvent(responseDtos);

        emailService.onPatientRegistered(patientEvent);


        return responseDtos;
    }

    @Override
    public Page<PatientResponseDto> getAllPatientDetails(Pageable pageable) {

        logger.info("Fetching patient details with pagination: page={}, size={}, sort={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        Page<Patient> patientPage = patientRepository.findAll(pageable);

        if (patientPage.isEmpty()) {
            logger.warn("No patients found for requested page={}", pageable.getPageNumber());
            return Page.empty();
        }

        Page<PatientResponseDto> patientResponseDtos = patientPage
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class));

        // ✅ Here use `patientResponseDtos`, not `pageable`
        logger.info("Successfully fetched {} patients on page={}, totalPages={}, totalElements={}",
                patientResponseDtos.getNumberOfElements(),   // count in current page
                patientResponseDtos.getNumber(),             // current page index
                patientResponseDtos.getTotalPages(),         // total pages
                patientResponseDtos.getTotalElements());     // total records

        return patientResponseDtos;

    }


}
