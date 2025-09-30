package com.example.HospitalManagmentSystemProject.controller;

import com.example.HospitalManagmentSystemProject.dto.AppoinmentRequestDto;
import com.example.HospitalManagmentSystemProject.dto.AppointmentResponseDto;
import com.example.HospitalManagmentSystemProject.service.AppointmentService;
import jakarta.validation.Valid;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private static  final Logger log= LoggerFactory.getLogger(AppointmentController.class);
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Register a new appointment
     * @param appoinmentRequestDto appointment request details
     * @return created appointment details
     */
    @PostMapping("/register")
    public ResponseEntity<AppointmentResponseDto> createAppointMent(@Valid @RequestBody AppoinmentRequestDto appoinmentRequestDto){
        log.info("Request received to create appointment: patientId={}, doctorId={}, time={}",
                appoinmentRequestDto.getPatientId(),
                appoinmentRequestDto.getDoctorId());
        AppointmentResponseDto appointment = appointmentService.createAppointment(appoinmentRequestDto);
        log.info("Appointment created successfully with id={}, patientId={}, doctorId={}",
                appointment.getAppointmentId());
        return new  ResponseEntity<>(appointment, HttpStatus.CREATED);

    }
    /**
     *  View Patient Appointments
     * Input: patientId
     * Return: list of appointments (past + future) with status
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByPatient(
            @PathVariable Long patientId) {

        log.info("API called: Fetch appointments for patientId={}", patientId);

        List<AppointmentResponseDto> appointments =
                appointmentService.getAppointmentByPatientId(patientId);

        if (appointments.isEmpty()) {
            log.warn("No appointments found for patientId={}", patientId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} appointments for patientId={}", appointments.size(), patientId);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointMent(@PathVariable("id") Long appointmentId){
        try {
            appointmentService.cancelAppointment(appointmentId);
            return ResponseEntity.ok("AppointMent Cancel SucessFull");
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/resedule")
    public ResponseEntity<String > rescheduleAppointMent(@PathVariable("id") Long appointmentId,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newTime){
        appointmentService.rescheduleAppointmen(appointmentId,newTime);
        return  ResponseEntity.ok("AppointMent Reschedule SucessFull");
    }
}
