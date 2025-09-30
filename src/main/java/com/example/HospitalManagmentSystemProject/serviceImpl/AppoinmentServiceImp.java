package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.AppoinmentRequestDto;
import com.example.HospitalManagmentSystemProject.dto.AppointmentResponseDto;
import com.example.HospitalManagmentSystemProject.entity.Appointment;
import com.example.HospitalManagmentSystemProject.entity.Doctor;
import com.example.HospitalManagmentSystemProject.entity.Patient;
import com.example.HospitalManagmentSystemProject.error.ResourceNotFoundException;
import com.example.HospitalManagmentSystemProject.repository.AppointmentRepository;
import com.example.HospitalManagmentSystemProject.repository.DoctorRepository;
import com.example.HospitalManagmentSystemProject.repository.PatientRepository;
import com.example.HospitalManagmentSystemProject.service.AppointmentService;
import com.example.HospitalManagmentSystemProject.service.DoctorService;
import com.example.HospitalManagmentSystemProject.service.PatientService;
import com.example.HospitalManagmentSystemProject.type.AppointmentStatus;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AppoinmentServiceImp implements AppointmentService {
    private static final long CUTOFF_HOURS = 24;
    private static final Logger log = LoggerFactory.getLogger(AppoinmentServiceImp.class);
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    @Autowired
    public ModelMapper modelMapper;

    public AppoinmentServiceImp(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    @Override
    public AppointmentResponseDto createAppointment(AppoinmentRequestDto appoinmentRequestDto) {
        Long patientId = appoinmentRequestDto.getPatientId();
        Long doctorId = appoinmentRequestDto.getDoctorId();

        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + patientId));

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + doctorId));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(appoinmentRequestDto.getLocalDateTime());
        appointment.setReason(appoinmentRequestDto.getReason());
        appointment.setStatus(AppointmentStatus.BOOKED);

        Appointment saveAppointment = appointmentRepository.save(appointment);

        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setAppointmentId(saveAppointment.getId());
        appointmentResponseDto.setDoctorName(saveAppointment.getDoctor().getName());
        appointmentResponseDto.setPatientName(saveAppointment.getPatient().getName());
        appointmentResponseDto.setReason(saveAppointment.getReason());
        appointmentResponseDto.setAppointmentTime(saveAppointment.getAppointmentTime());
        appointmentResponseDto.setStatus(saveAppointment.getStatus().name());
        log.info("Appointment created: patientId={}, doctorId={}, specialization={}, time={}",
                patient.getId(),
                doctor.getId(),
                doctor.getSpecialization(),
                saveAppointment.getAppointmentTime());
        return appointmentResponseDto;
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentByPatientId(Long patientId) {
        log.info("Fetching appointments for patientId={}", patientId);
        List<Appointment> appointments = appointmentRepository.findByPatient_IdOrderByAppointmentTimeDesc(patientId);
        if (appointments.isEmpty()) {
            log.warn("No appointments found for patientId={}", patientId);
            return List.of();
        }
        return appointments.stream()
                .map(appt -> {
                    AppointmentResponseDto dto = modelMapper.map(appt, AppointmentResponseDto.class);

                    // Add past/future classification
                    if (appt.getAppointmentTime().isBefore(LocalDateTime.now())) {
                        dto.setStatus("PAST - " + appt.getStatus().name());
                    } else {
                        dto.setStatus("FUTURE - " + appt.getStatus().name());
                    }

                    return dto;
                })
                .collect(Collectors.toList());


    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment id is notfound"));

        if (!appointment.getStatus().equals(AppointmentStatus.BOOKED)) {
            throw new RuntimeException("Only scheduled appointments can be cancelled");
        }

        long hoursLeft = Duration.between(LocalDateTime.now(), appointment.getAppointmentTime()).toHours();
        if (hoursLeft < CUTOFF_HOURS) {
            throw new RuntimeException("Cannot cancel within 24 hours of appointment time");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancelledAt(LocalDateTime.now());
        appointmentRepository.save(appointment);
    }

    @Override
    public void rescheduleAppointmen(Long appointmentId, LocalDateTime time) {
        Appointment appointment = appointmentRepository.findById(appointmentId).
                orElseThrow(() -> new ResourceNotFoundException("AppointMentId is not found"));

        if (!appointment.getStatus().equals(AppointmentStatus.BOOKED)) {
            throw new ResourceNotFoundException("Only scheduled appointments can be rescheduled");
        }

        long currentHour = Duration.between(LocalDateTime.now(), appointment.getAppointmentTime()).toHours();
        if (currentHour < CUTOFF_HOURS) {
            throw new ResourceNotFoundException("Cannot reschedule within 24 hours of appointment time");
        }

        appointment.setPreviousAppointmentTime(appointment.getAppointmentTime());
        appointment.setAppointmentTime(time);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        appointment.setRescheduleCount(appointment.getRescheduleCount() + 1);
        appointmentRepository.save(appointment);
    }
}
