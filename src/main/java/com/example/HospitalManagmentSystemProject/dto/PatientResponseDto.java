package com.example.HospitalManagmentSystemProject.dto;

import java.time.LocalDate;
import java.util.List;

public class PatientResponseDto {

    private Long id;
    private String name;
    private String gmail;
    private String gender;
    private LocalDate birthdate;
    private LocalDate createDate;
    private LocalDate updateDate;

    private InsuranceResponseDto insurance;
    private List<Long> appointmentIds;

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

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public InsuranceResponseDto getInsurance() {
        return insurance;
    }

    public void setInsurance(InsuranceResponseDto insurance) {
        this.insurance = insurance;
    }

    public List<Long> getAppointmentIds() {
        return appointmentIds;
    }

    public void setAppointmentIds(List<Long> appointmentIds) {
        this.appointmentIds = appointmentIds;
    }
}
