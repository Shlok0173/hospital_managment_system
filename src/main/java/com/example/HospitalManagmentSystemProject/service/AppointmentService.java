package com.example.HospitalManagmentSystemProject.service;

import com.example.HospitalManagmentSystemProject.dto.AppoinmentRequestDto;
import com.example.HospitalManagmentSystemProject.dto.AppointmentResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface AppointmentService {

   public AppointmentResponseDto createAppointment( AppoinmentRequestDto appoinmentRequestDto);

   public List<AppointmentResponseDto> getAppointmentByPatientId(Long  patientId);

   public void cancelAppointment(Long appointmentId);

   public void rescheduleAppointmen(Long appointmentId, LocalDateTime time);
}
