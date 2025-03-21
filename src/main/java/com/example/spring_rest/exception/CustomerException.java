package com.example.spring_rest.exception;

public class CustomerException extends RuntimeException {
    public CustomerException(String message) {
        super(message);
    }
}
