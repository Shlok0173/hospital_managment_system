package com.example.HospitalManagmentSystemProject.dto;

import com.example.HospitalManagmentSystemProject.type.InsuranceStatus;

import java.time.LocalDate;

public class InsuranceResponseDto {
    private String patientName;
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;
    private LocalDate createAt;

    private InsuranceStatus status;
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPolicyNumber(String policyNumber) {
        return this.policyNumber;
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

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public InsuranceStatus getStatus() {
        return status;
    }

    public void setStatus(InsuranceStatus status) {
        this.status = status;
    }

    public InsuranceResponseDto(String patientName, String policyNumber, String provider, LocalDate validUntil, LocalDate createAt) {
        this.patientName = patientName;
        this.policyNumber = policyNumber;
        this.provider = provider;
        this.validUntil = validUntil;
        this.createAt = createAt;
    }

    public InsuranceResponseDto() {
    }
}
