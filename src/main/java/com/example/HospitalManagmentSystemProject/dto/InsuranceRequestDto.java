package com.example.HospitalManagmentSystemProject.dto;

import java.time.LocalDate;

public class InsuranceRequestDto {
    private Long patientId;
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public InsuranceRequestDto(Long patientId, String policyNumber, String provider, LocalDate validUntil) {
        this.patientId = patientId;
        this.policyNumber = policyNumber;
        this.provider = provider;
        this.validUntil = validUntil;
    }

    public InsuranceRequestDto() {
    }
}
