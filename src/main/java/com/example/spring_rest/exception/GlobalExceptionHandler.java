package com.example.spring_rest.exception;

import com.example.spring_rest.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.warn("Validation errors: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Обробка інших помилок (якщо вони виникають)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.warn("Client error: {}", ex.getMessage());
        return new ResponseEntity<>("Помилка: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<Object> handleException(CustomerException e) {
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<Object> handleException(NotFoundException e) {
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, true, e.getMessage(), null);
    }
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
//        log.warn("Client error: {}", ex.getMessage());
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    // Обробка невідомих помилок
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleAllExceptions(Exception ex) {
//        log.error("Internal server error: {}", ex.getMessage(), ex);
//        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
