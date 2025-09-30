package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.InsuranceRequestDto;
import com.example.HospitalManagmentSystemProject.dto.InsuranceResponseDto;
import com.example.HospitalManagmentSystemProject.entity.Insurance;
import com.example.HospitalManagmentSystemProject.entity.Patient;
import com.example.HospitalManagmentSystemProject.repository.InsuranceRepository;
import com.example.HospitalManagmentSystemProject.repository.PatientRepository;
import com.example.HospitalManagmentSystemProject.service.InsuranceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsuranceServiceImpTest {
    @InjectMocks
    private InsuranceServiceImp insuranceService;

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private PatientRepository patientRepository;

    @Test
    void assignInsurance_whenPatientExists_AssignSuccessfully() {

        InsuranceRequestDto insuranceRequest = new InsuranceRequestDto();
        insuranceRequest.setPatientId(1L);
        insuranceRequest.setProvider("HDFC");
        insuranceRequest.setPolicyNumber("HDFC3456");
        insuranceRequest.setValidUntil(LocalDate.now().plusYears(1));

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("SHLOK");

        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Mockito.when(insuranceRepository.save(any(Insurance.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        InsuranceResponseDto response = insuranceService.assignInsurance(insuranceRequest);

        assertThat(response.getPatientName()).isEqualTo("SHLOK");
        assertThat(response.getProvider()).isEqualTo("HDFC");
    }

}
