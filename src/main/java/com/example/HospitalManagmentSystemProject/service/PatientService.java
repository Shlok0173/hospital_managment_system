package com.example.HospitalManagmentSystemProject.service;

import com.example.HospitalManagmentSystemProject.dto.PatientRequestDto;
import com.example.HospitalManagmentSystemProject.dto.PatientResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PatientService {

    public PatientResponseDto registerPatient(PatientRequestDto patientRequestDto);

    public Page<PatientResponseDto> getAllPatientDetails( Pageable pageable);
}
