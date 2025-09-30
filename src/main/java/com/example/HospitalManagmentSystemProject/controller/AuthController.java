package com.example.HospitalManagmentSystemProject.controller;

import com.example.HospitalManagmentSystemProject.dto.LoginRequestDto;
import com.example.HospitalManagmentSystemProject.dto.LoginResponseDto;
import com.example.HospitalManagmentSystemProject.dto.SignUpResponseDto;
import com.example.HospitalManagmentSystemProject.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login( @RequestBody LoginRequestDto loginRequestDto){
       return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody LoginRequestDto sineUpRequestDto){
        return  ResponseEntity.ok(authService.signup(sineUpRequestDto));
    }
}
