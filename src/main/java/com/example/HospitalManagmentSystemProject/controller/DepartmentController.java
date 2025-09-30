package com.example.HospitalManagmentSystemProject.controller;

import com.example.HospitalManagmentSystemProject.dto.DepartmentRequestDto;
import com.example.HospitalManagmentSystemProject.dto.DepartmentResponseDto;
import com.example.HospitalManagmentSystemProject.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/department")
@RestController
public class DepartmentController {

    private static final Logger logger= LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Register a new department
     * @param departmentRequestDto input payload
     * @return created DepartmentResponseDto
     */
    @PostMapping("/register")
    public  ResponseEntity<DepartmentResponseDto> registerDepartment( @Valid @RequestBody DepartmentRequestDto departmentRequestDto){
        logger.info("Received request to register department with name: {}", departmentRequestDto.getName());
        DepartmentResponseDto departmentResponseDto = departmentService.registerDepartMent(departmentRequestDto);
        return new ResponseEntity<>(departmentResponseDto, HttpStatus.CREATED);
    }
}
