package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.DepartmentRequestDto;
import com.example.HospitalManagmentSystemProject.dto.DepartmentResponseDto;
import com.example.HospitalManagmentSystemProject.dto.DoctorResponseDto;
import com.example.HospitalManagmentSystemProject.entity.Department;
import com.example.HospitalManagmentSystemProject.entity.Doctor;
import com.example.HospitalManagmentSystemProject.repository.DepartmentRepository;
import com.example.HospitalManagmentSystemProject.repository.DoctorRepository;
import com.example.HospitalManagmentSystemProject.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartMentServiceImp implements DepartmentService {


    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public DepartMentServiceImp(DepartmentRepository departmentRepository, DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentResponseDto registerDepartMent(DepartmentRequestDto departmentRequestDto) {

        List<Doctor> allById = doctorRepository.findAllById(departmentRequestDto.getDoctorid());

        if (allById.isEmpty()) {
            throw new RuntimeException("No doctors found for given IDs: " + departmentRequestDto.getDoctorid());
        }

        Department department = new Department();
        department.setName(departmentRequestDto.getName());
        department.setDoctors(allById);
        Department saveDepartment = departmentRepository.save(department);
        DepartmentResponseDto responseDto = modelMapper.map(saveDepartment, DepartmentResponseDto.class);
        // Ensure doctor details mapped properly
        responseDto.setDoctorIds(
                saveDepartment.getDoctors().stream()
                        .map(doc->modelMapper.map(doc, DoctorResponseDto.class))
                        .collect(Collectors.toList())
        );

        return responseDto;
    }

}
