package com.example.HospitalManagmentSystemProject.dto;

public class LoginResponseDto {
     Long id;

     String jwt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public LoginResponseDto(Long id, String jwt) {
        this.id = id;
        this.jwt = jwt;
    }

    public LoginResponseDto() {
    }
}
