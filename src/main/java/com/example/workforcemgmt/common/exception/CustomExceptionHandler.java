package com.example.workforcemgmt.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex){
        Map<String,Object>error =new HashMap<>();
        error.put("status",HttpStatus.NOT_FOUND.value());
        error.put("error","Not Found");
        error.put("message",ex.getMessage());
        error.put("timestamp",LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex){
        Map<String,Object>error=new HashMap<>();
        error.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error","Server Error");
        error.put("message",ex.getMessage());
        error.put("timestamp",LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
