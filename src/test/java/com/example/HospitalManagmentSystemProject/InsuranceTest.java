package com.example.HospitalManagmentSystemProject;

import com.example.HospitalManagmentSystemProject.entity.Insurance;
import com.example.HospitalManagmentSystemProject.entity.Patient;
import com.example.HospitalManagmentSystemProject.repository.InsuranceRepository;
import com.example.HospitalManagmentSystemProject.repository.PatientRepository;
import com.example.HospitalManagmentSystemProject.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class InsuranceTest {

    @InjectMocks
    private InsuranceService insuranceService;

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private PatientRepository patientRepository;



}
