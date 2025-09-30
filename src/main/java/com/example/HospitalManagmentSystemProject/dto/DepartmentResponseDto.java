package com.example.HospitalManagmentSystemProject.dto;


import java.util.List;

public class DepartmentResponseDto {

    private Long id;
    private String name;
    private List<DoctorResponseDto> doctorIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DoctorResponseDto> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<DoctorResponseDto> doctorIds) {
        this.doctorIds = doctorIds;
    }
}
