package com.pm.patientservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error->errors.put(error.getField(),error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {

        log.warn("Email already exist{}",ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message","Email Already Exist");
        return ResponseEntity.badRequest().body(errors);


    }


}
