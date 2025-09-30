package com.example.HospitalManagmentSystemProject.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppoinmentRequestDto {
    private Long patientId;
    private Long doctorId;

    private LocalDateTime localDateTime;
    private String reason;

    public AppoinmentRequestDto(Long patientId, Long doctorId, LocalDateTime localDateTime, String reason) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.localDateTime = localDateTime;
        this.reason = reason;
    }

    public AppoinmentRequestDto() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
