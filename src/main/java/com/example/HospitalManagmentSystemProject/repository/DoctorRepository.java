package com.example.HospitalManagmentSystemProject.repository;

import com.example.HospitalManagmentSystemProject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    boolean existsByemail(String email);

    List<Doctor> findBySpecialization(String specialization);
}
