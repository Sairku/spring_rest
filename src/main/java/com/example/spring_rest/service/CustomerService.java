package com.example.spring_rest.service;

import com.example.spring_rest.dto.CustomerFacade;
import com.example.spring_rest.dto.CustomerRequest;
import com.example.spring_rest.dto.CustomerResponse;
import com.example.spring_rest.model.Customer;
import com.example.spring_rest.model.Account;
import com.example.spring_rest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.spring_rest.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerFacade customerFacade;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerFacade customerFacade) {
        this.customerRepository = customerRepository;
        this.customerFacade = customerFacade;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerFacade.toEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return customerFacade.toResponse(savedCustomer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Customer updatedCustomer = customerFacade.toEntity(customerRequest);
        updatedCustomer.setId(existingCustomer.getId()); // Зберігаємо існуючий ID
        Customer savedCustomer = customerRepository.save(updatedCustomer);
        return customerFacade.toResponse(savedCustomer);
    }
    public Page<CustomerResponse> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerFacade::toResponse);
    }
}
