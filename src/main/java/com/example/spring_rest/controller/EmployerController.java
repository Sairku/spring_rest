package com.example.spring_rest.controller;

import com.example.spring_rest.dto.EmployerRequest;
import com.example.spring_rest.dto.EmployerResponse;
import com.example.spring_rest.service.CustomerService;
import com.example.spring_rest.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@Validated
public class EmployerController {
    private final EmployerService employerService;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<EmployerResponse> createEmployer(@RequestBody @Valid EmployerRequest employerRequest) {
        log.info("Creating new employer with name: {}", employerRequest.getName());
        EmployerResponse employerResponse = employerService.createEmployer(employerRequest);
        return ResponseEntity.status(201).body(employerResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long id) {
        log.info("Fetching employer by ID: {}", id);
        EmployerResponse employerResponse = employerService.getEmployerById(id);
        return ResponseEntity.ok(employerResponse);
    }
    @GetMapping
    public List<EmployerResponse> getAllEmployers() {
        log.info("Fetching all employers");
        return employerService.getAllEmployers();
    }

    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable Long id) {
        log.info("Deleting employer with ID: {}", id);
        employerService.deleteEmployer(id);
    }

}
