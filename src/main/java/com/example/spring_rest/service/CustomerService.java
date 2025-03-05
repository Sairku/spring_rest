package com.example.spring_rest.service;

import com.example.spring_rest.dto.CustomerFacade;
import com.example.spring_rest.dto.CustomerRequest;
import com.example.spring_rest.dto.CustomerResponse;
import com.example.spring_rest.model.Customer;
import com.example.spring_rest.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerFacade customerFacade;
    private final PasswordEncoder passwordEncoder; // Додаємо PasswordEncoder

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerFacade customerFacade, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerFacade = customerFacade;
        this.passwordEncoder = passwordEncoder; // Ініціалізуємо PasswordEncoder
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerFacade.toEntity(customerRequest);
        customer.setPassword(passwordEncoder.encode(customer.getPassword())); // Кодуємо пароль перед збереженням
        Customer savedCustomer = customerRepository.save(customer);
        return customerFacade.toResponse(savedCustomer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Customer updatedCustomer = customerFacade.toEntity(customerRequest);
        updatedCustomer.setId(existingCustomer.getId()); // Зберігаємо існуючий ID
        updatedCustomer.setPassword(passwordEncoder.encode(updatedCustomer.getPassword())); // Кодуємо пароль перед оновленням
        Customer savedCustomer = customerRepository.save(updatedCustomer);
        return customerFacade.toResponse(savedCustomer);
    }

    public Page<CustomerResponse> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerFacade::toResponse);
    }
}