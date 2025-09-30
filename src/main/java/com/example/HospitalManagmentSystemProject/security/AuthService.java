package com.example.HospitalManagmentSystemProject.security;

import com.example.HospitalManagmentSystemProject.dto.LoginRequestDto;
import com.example.HospitalManagmentSystemProject.dto.LoginResponseDto;
import com.example.HospitalManagmentSystemProject.dto.SignUpResponseDto;
import com.example.HospitalManagmentSystemProject.entity.User;
import com.example.HospitalManagmentSystemProject.repository.UserRepository;
import com.example.HospitalManagmentSystemProject.type.RoleType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );
        User user=(User)authentication.getPrincipal();

        String token= authUtil.generateAcessToken(user);

        return  new LoginResponseDto(user.getId(),token);
    }

    public SignUpResponseDto signup(LoginRequestDto sineUpRequestDto) {
        User user=userRepository.findByUsername(sineUpRequestDto.getUsername()).orElse(null);
        if(user != null) throw new IllegalArgumentException("User already exists");

        User newUser=new User();
        newUser.setUsername(sineUpRequestDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(sineUpRequestDto.getPassword()));
        User saved = userRepository.save(newUser);

        // manual mapping
        SignUpResponseDto response = new SignUpResponseDto();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        System.out.println(saved.getUsername());// only username
        return  response;
    }
}
