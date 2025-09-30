package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.DoctorRequestDto;
import com.example.HospitalManagmentSystemProject.dto.DoctorResponseDto;
import com.example.HospitalManagmentSystemProject.entity.Doctor;
import com.example.HospitalManagmentSystemProject.repository.DoctorRepository;
import com.example.HospitalManagmentSystemProject.repository.PatientRepository;
import com.example.HospitalManagmentSystemProject.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DoctorServiceImp implements DoctorService {


    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;


    private static  final Logger logger= LoggerFactory.getLogger(DoctorServiceImp.class);

    @Override
    public List<DoctorResponseDto> getAllDoctos(int page, int size, String orderBy) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(orderBy));
        Page<Doctor> doctorPage = doctorRepository.findAll(pageRequest);

        return doctorPage.getContent()   // converts Page -> List
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Registers a new doctor.
     * <p>
     * Converts DoctorRequestDTO into Doctor entity, saves it into the database,
     * then converts the saved entity into DoctorResponseDTO and returns it.
     *
     * @param doctorRequestDto doctor registration request data
     * @return DoctorResponseDTO containing saved doctor details
     */
    @Override
    public DoctorResponseDto registerDoctor(DoctorRequestDto doctorRequestDto) {
        logger.info("Attempting to register doctor with email: {}", doctorRequestDto.getEmail());

        if(doctorRepository.existsByemail(doctorRequestDto.getEmail())){
            logger.warn("Doctor registration failed: Email {} already exists", doctorRequestDto.getEmail());
            throw new IllegalArgumentException("Email already exists: " + doctorRequestDto.getEmail());
        }
       Doctor doctor = modelMapper.map(doctorRequestDto,Doctor.class);
       Doctor saveDoctor =doctorRepository.save(doctor);

        logger.debug("Doctor saved successfully with ID: {}", saveDoctor.getId());
        DoctorResponseDto map = modelMapper.map(saveDoctor, DoctorResponseDto.class);
        logger.info("Doctor registered successfully with ID: {}", map.getId());
        return map;
    }

    @Override
    public List <DoctorResponseDto> getDoctorBySpecialization(String specialization) {
        logger.info("Fetching doctors with specialization: {}", specialization);

        List<Doctor> bySpecialization = doctorRepository.findBySpecialization(specialization);
        if(bySpecialization.isEmpty() && bySpecialization==null){
            logger.warn("No doctors found with specialization: {}", specialization);

        }
          List<DoctorResponseDto> responseDtos=bySpecialization.stream().map(doc -> modelMapper.map(doc, DoctorResponseDto.class)).collect(Collectors.toList());
        return  responseDtos;

}
}