package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.entity.Insurance;
import com.example.HospitalManagmentSystemProject.repository.InsuranceRepository;
import com.example.HospitalManagmentSystemProject.type.InsuranceStatus;
import com.example.HospitalManagmentSystemProject.utill.PolicyExpiryScheduler;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.any;

@ExtendWith(MockitoExtension.class)
public class InsuranceSedulingTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @InjectMocks
    private PolicyExpiryScheduler policyExpiryScheduler;


    @Test
    void assignInsurance_withGivenTime_expiredCheckSuccessful() {

    }
}
