package com.example.spring_rest.controller;

import com.example.spring_rest.dto.CustomerFacade;
import com.example.spring_rest.dto.CustomerRequest;
import com.example.spring_rest.dto.CustomerResponse;
import com.example.spring_rest.model.Customer;
import com.example.spring_rest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerFacade customerFacade) {
        this.customerService = customerService;
        this.customerFacade = customerFacade;
    }

    // Отримати інформацію про окремого користувача
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);  // Повертаємо Customer, а не CustomerResponse
        CustomerResponse response = customerFacade.toResponse(customer);  // Перетворюємо Customer на CustomerResponse
        return ResponseEntity.ok(response);
    }

    // Створити користувача
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse createdCustomer = customerService.createCustomer(customerRequest);
        return ResponseEntity.status(201).body(createdCustomer);
    }

    // Оновити дані користувача
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Отримати список користувачів з пагінацією
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(customerService.getAll(pageable));
    }
}
