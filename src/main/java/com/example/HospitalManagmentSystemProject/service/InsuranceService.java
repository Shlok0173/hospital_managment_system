package com.example.HospitalManagmentSystemProject.service;

import com.example.HospitalManagmentSystemProject.dto.InsuranceRequestDto;
import com.example.HospitalManagmentSystemProject.dto.InsuranceResponseDto;
import com.example.HospitalManagmentSystemProject.entity.Insurance;
import com.example.HospitalManagmentSystemProject.entity.Patient;
import org.springframework.stereotype.Service;

@Service
public interface InsuranceService {

    public InsuranceResponseDto assignInsurance(InsuranceRequestDto insuranceRequestDto);
}
