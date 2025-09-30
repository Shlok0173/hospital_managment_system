package com.example.HospitalManagmentSystemProject.repository;

import com.example.HospitalManagmentSystemProject.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
