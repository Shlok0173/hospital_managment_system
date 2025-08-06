package com.example.HospitalManagmentSystemProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Entity
public class entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Column(unique = true)
    private String gmail;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate birthdate;
}
