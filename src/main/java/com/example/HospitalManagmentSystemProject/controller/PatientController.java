package com.example.HospitalManagmentSystemProject.controller;

import com.example.HospitalManagmentSystemProject.dto.PatientRequestDto;
import com.example.HospitalManagmentSystemProject.dto.PatientResponseDto;
import com.example.HospitalManagmentSystemProject.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
private static  final Logger logger= LoggerFactory.getLogger(PatientController.class);
    @Autowired
    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService=patientService;
    }
    /**
     * Register a new patient
     */
    @PostMapping("/register")
    public ResponseEntity<PatientResponseDto> registerPatient(@RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto responseDto = patientService.registerPatient(patientRequestDto);
        logger.info("✅ Patient registered successfully with ID: {}", responseDto.getId());
        logger.debug("📌 Full Patient Response: {}", responseDto);

        return  ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


    /**
     * Fetch all patients with pagination and sorting.
     * Accessible only for authenticated users (JWT Secured).
     */
    @GetMapping
    public ResponseEntity<Page<PatientResponseDto>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        logger.info("Request received to fetch patients: page={}, size={}, sortBy={}, direction={}",
                page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PatientResponseDto> patientPage = patientService.getAllPatientDetails(pageable);

        if (patientPage.isEmpty()) {
            logger.warn("No patients found for requested page={}, size={}", page, size);
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        logger.info("Returning {} patients, currentPage={}, totalPages={}, totalElements={}",
                patientPage.getNumberOfElements(),
                patientPage.getNumber(),
                patientPage.getTotalPages(),
                patientPage.getTotalElements());

        return ResponseEntity.ok(patientPage);
    }
}
