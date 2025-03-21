package com.example.spring_rest.exception;

import com.example.spring_rest.util.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice  // Це вказує Spring, що цей клас відповідає за обробку помилок у контролерах
public class GlobalExceptionHandler {

    // Обробка помилок валідації
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Отримуємо всі помилки валідації та перетворюємо їх у Map (поле -> повідомлення)
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Повертаємо помилки з кодом 400 (Bad Request)
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Обробка інших помилок (якщо вони виникають)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
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
}
