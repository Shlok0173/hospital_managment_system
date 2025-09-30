package com.example.HospitalManagmentSystemProject.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.example.HospitalManagmentSystemProject.config.Appconfig.*;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
public class WebSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/public/**", "/auth/**").permitAll()
                                .requestMatchers("/patient/register", "/doctor/register", "/department/register", "/appointment/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/doctor/specialization/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/appointment/patient/**").permitAll()
                                .requestMatchers("/appointment/*/cancel").authenticated()
                                .requestMatchers("/appointment/*/reschedule").authenticated()
                                .requestMatchers("/kafka/**", "/actuator/**").permitAll()
                                .requestMatchers(("/insurance/assign")).authenticated()
                                // ✅ Secured endpoints (JWT required)
                                .requestMatchers(HttpMethod.GET, "/patient/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/patient/**").hasAnyRole("PATIENT", "ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    // @Bean
    UserDetailsService userDetailsService() {
        UserDetails userOne = User.withUsername("admin")
                .password(passwordEncoder.encode("pass"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withUsername("patient")
                .password(passwordEncoder.encode("pass"))
                .roles("PATIENT")
                .build();
        return new InMemoryUserDetailsManager(userOne, user2);

    }
}