package com.example.HospitalManagmentSystemProject.controller;

import com.example.HospitalManagmentSystemProject.dto.DoctorRequestDto;
import com.example.HospitalManagmentSystemProject.dto.DoctorResponseDto;
import com.example.HospitalManagmentSystemProject.service.DoctorService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    private Logger logger= (Logger) LoggerFactory.getLogger(DoctorController.class);
    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctor(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "09") int size,
            @RequestParam (defaultValue = "id") String orderBy ){

          logger.info("Request received: Fetching doctors with page={}, size={}, orderBy={}");
        List<DoctorResponseDto> doctors = doctorService.getAllDoctos(page,
                size,
                orderBy);

        if (doctors.isEmpty()) {
            logger.warn("Doctor Will Not Found ");
            return ResponseEntity.noContent().build();
        }
        return  new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * Register a new doctor
     * @param doctorRequestDto doctor registration request
     * @return created doctor details
     */

    @PostMapping("/register")
    public ResponseEntity<DoctorResponseDto> registerDoctor(@RequestBody DoctorRequestDto doctorRequestDto){
        logger.info("Registering new doctor with email: {}", doctorRequestDto.getEmail());
        DoctorResponseDto doctorResponseDto = doctorService.registerDoctor(doctorRequestDto);
        logger.info("Doctor registered successfully with id: {}", doctorResponseDto.getId());
        return new ResponseEntity<>(doctorResponseDto,HttpStatus.CREATED);
    }

    @GetMapping("/specialization/{specialization}")

    public ResponseEntity<List<DoctorResponseDto>> getDoctorsBySpecialization(@PathVariable String specialization){
        logger.info("Received request to fetch doctors by specialization: {}", specialization);

        List<DoctorResponseDto> doctors = doctorService.getDoctorBySpecialization(specialization);

        logger.info("Returning {} doctors for specialization: {}", doctors.size(), specialization);

        return ResponseEntity.ok(doctors);
    }
    }
