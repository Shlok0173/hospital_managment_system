package com.example.HospitalManagmentSystemProject.error;

public class UserNotFoundException extends RuntimeException{
    public static String UserNotFoundException;

    public UserNotFoundException(String message) {
        super(message);
    }
}
