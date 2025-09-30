package com.example.HospitalManagmentSystemProject.service;


import com.example.HospitalManagmentSystemProject.dto.DoctorRequestDto;
import com.example.HospitalManagmentSystemProject.dto.DoctorResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DoctorService {

     public List<DoctorResponseDto> getAllDoctos(int size,int page,String orderBy);

     public DoctorResponseDto registerDoctor(DoctorRequestDto doctorRequestDto);

    public List<DoctorResponseDto> getDoctorBySpecialization(String specialization);
}
