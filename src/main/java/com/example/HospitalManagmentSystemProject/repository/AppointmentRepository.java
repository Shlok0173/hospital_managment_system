package com.example.HospitalManagmentSystemProject.repository;

import com.example.HospitalManagmentSystemProject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    // Get all appointments for a patient, latest first
    List<Appointment> findByPatient_IdOrderByAppointmentTimeDesc(Long patientId);
}
