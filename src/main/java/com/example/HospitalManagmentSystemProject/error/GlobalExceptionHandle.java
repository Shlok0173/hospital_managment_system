package com.example.HospitalManagmentSystemProject.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handelUserNotFoundException(UserNotFoundException exception){
      ApiError apiError=new ApiError("user not found for this username "+ exception.getMessage(), HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(apiError,apiError.getStatusCode());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(JwtException.class)
    public  ResponseEntity<ApiError>handelJwtException(JwtException jwtException){
        ApiError apiError=new ApiError("jwt exception"+jwtException.getMessage(),HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(apiError,apiError.getStatusCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleresourceNotFound( ResourceNotFoundException exception){
    ApiError apiError=new ApiError(
            LocalDateTime.now(),
            exception.getMessage(),
            HttpStatus.NOT_FOUND
    );
    return new  ResponseEntity(apiError,HttpStatus.NOT_FOUND);
    }
}
