package com.example.HospitalManagmentSystemProject.repository;

import com.example.HospitalManagmentSystemProject.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
