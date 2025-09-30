package com.example.HospitalManagmentSystemProject.controller;

import com.example.HospitalManagmentSystemProject.dto.InsuranceRequestDto;
import com.example.HospitalManagmentSystemProject.dto.InsuranceResponseDto;
import com.example.HospitalManagmentSystemProject.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance")
public class InsuranceController {

    @Autowired
    private final InsuranceService insuranceService;


    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }


    @PostMapping("/assign")
    public ResponseEntity<InsuranceResponseDto> assignInsurance(@RequestBody InsuranceRequestDto insuranceRequestDto){
        InsuranceResponseDto insuranceResponse = insuranceService.assignInsurance(insuranceRequestDto);
        return new ResponseEntity<>(insuranceResponse, HttpStatus.CREATED);
    }
}
