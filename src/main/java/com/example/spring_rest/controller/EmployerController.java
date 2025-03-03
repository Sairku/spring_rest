package com.example.spring_rest.controller;

import com.example.spring_rest.dto.EmployerRequest;
import com.example.spring_rest.dto.EmployerResponse;
import com.example.spring_rest.model.Employer;
import com.example.spring_rest.service.CustomerService;
import com.example.spring_rest.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
@Validated
public class EmployerController {
    private final EmployerService employerService;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<EmployerResponse> createEmployer(@RequestBody @Valid EmployerRequest employerRequest) {
        EmployerResponse employerResponse = employerService.createEmployer(employerRequest);
        return ResponseEntity.status(201).body(employerResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long id) {
        EmployerResponse employerResponse = employerService.getEmployerById(id);
        return ResponseEntity.ok(employerResponse);
    }
    @GetMapping
    public List<Employer> getAllEmployers() {
        return employerService.getAllEmployers();
    }

    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
    }

}
