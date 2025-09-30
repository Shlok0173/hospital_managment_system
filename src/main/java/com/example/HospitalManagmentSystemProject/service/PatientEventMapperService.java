package com.example.HospitalManagmentSystemProject.service;

import com.example.HospitalManagmentSystemProject.dto.PatientEvent;
import com.example.HospitalManagmentSystemProject.dto.PatientResponseDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class PatientEventMapperService {

    /**
     * Converts PatientResponseDto to PatientEvent
     * Adds eventId and occurredAt for idempotency and event tracking
     */
    public PatientEvent mapToPatientEvent(PatientResponseDto patientResponseDto) {
        PatientEvent event = new PatientEvent();

        event.setId(patientResponseDto.getId());
        event.setName(patientResponseDto.getName());
        event.setGmail(patientResponseDto.getGmail());
        event.setGender(patientResponseDto.getGender());
        event.setBirthdate(patientResponseDto.getBirthdate());

        // ✅ Generate unique eventId for idempotency
        event.setEventId(UUID.randomUUID().toString());

        // ✅ Set timestamp for auditing / ordering
        event.setOccurredAt(Instant.now());

        return event;
    }
}
