package com.example.HospitalManagmentSystemProject.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class DoctorRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String specialization;

    @NotBlank
    @Column(unique = true)
    private String email;

    public DoctorRequestDto(String name, String specialization, String email) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
    }

    public DoctorRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
