package com.example.HospitalManagmentSystemProject.security;

import com.example.HospitalManagmentSystemProject.entity.User;
import com.example.HospitalManagmentSystemProject.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j

public class JwtAuthFilter  extends OncePerRequestFilter {

    public  static  final Logger logger= LoggerFactory.getLogger(JwtAuthFilter.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtil authUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("incoming request: {}",request.getRequestURI());

        final  String requestTokenHeader=request.getHeader("Authorization");

        if(requestTokenHeader ==null || !requestTokenHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=requestTokenHeader.split("Bearer")[1];
        String username=authUtil.getUserNameFromToken(token);

        if (username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user=userRepository.findByUsername(username).orElseThrow();
            UsernamePasswordAuthenticationToken tokens=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(tokens);
        }
        filterChain.doFilter(request,response);
    }
}