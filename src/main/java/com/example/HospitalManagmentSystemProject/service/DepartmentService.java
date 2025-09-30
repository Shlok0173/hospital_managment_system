package com.example.HospitalManagmentSystemProject.service;

import com.example.HospitalManagmentSystemProject.dto.DepartmentRequestDto;
import com.example.HospitalManagmentSystemProject.dto.DepartmentResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {

    public DepartmentResponseDto registerDepartMent(DepartmentRequestDto departmentRequestDto);

}
