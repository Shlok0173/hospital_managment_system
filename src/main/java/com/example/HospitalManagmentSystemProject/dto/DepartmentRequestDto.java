package com.example.HospitalManagmentSystemProject.dto;

import com.example.HospitalManagmentSystemProject.entity.Doctor;

import java.util.List;

public class DepartmentRequestDto {

    private String name;

    private List<Long> doctorid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(List<Long> doctorid) {
        this.doctorid = doctorid;
    }

    public DepartmentRequestDto(String name, List<Long> doctorid) {
        this.name = name;
        this.doctorid = doctorid;
    }

    public DepartmentRequestDto() {
    }
}
