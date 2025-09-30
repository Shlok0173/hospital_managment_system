package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.InsuranceRequestDto;
import com.example.HospitalManagmentSystemProject.dto.InsuranceResponseDto;
import com.example.HospitalManagmentSystemProject.entity.Insurance;
import com.example.HospitalManagmentSystemProject.entity.Patient;
import com.example.HospitalManagmentSystemProject.repository.InsuranceRepository;
import com.example.HospitalManagmentSystemProject.repository.PatientRepository;
import com.example.HospitalManagmentSystemProject.service.InsuranceService;
import com.example.HospitalManagmentSystemProject.type.InsuranceStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsuranceServiceImp implements InsuranceService {


    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    @Override
    public InsuranceResponseDto assignInsurance(InsuranceRequestDto insuranceRequestDto) {
        Patient patient = patientRepository.findById(insuranceRequestDto.getPatientId()).orElseThrow(() -> new RuntimeException("Patient Id will not found"));
        if (patient.getInsurance() != null) {
            throw new IllegalStateException("Patient already has an insurance policy assigned.");
        }
        Insurance insurance = new Insurance();
        insurance.setPatient(patient);
        insurance.setValidUntil(insuranceRequestDto.getValidUntil());
        insurance.setPolicyNumber(insuranceRequestDto.getPolicyNumber());
        insurance.setProvider(insuranceRequestDto.getProvider());
        insurance.setStatus(InsuranceStatus.ACTIVE);
        Insurance savedInsurance = insuranceRepository.save(insurance);

        InsuranceResponseDto insuranceResponseDto = new InsuranceResponseDto();
        insuranceResponseDto.setPatientName(patient.getName());
        insuranceResponseDto.setProvider(savedInsurance.getProvider());
        insuranceResponseDto.setPolicyNumber(savedInsurance.getPolicyNumber());
        insuranceResponseDto.setCreateAt(savedInsurance.getCreateAt());
        insuranceResponseDto.setValidUntil(savedInsurance.getValidUntil());
        insuranceResponseDto.setStatus(savedInsurance.getStatus());
        return insuranceResponseDto;
    }
}
