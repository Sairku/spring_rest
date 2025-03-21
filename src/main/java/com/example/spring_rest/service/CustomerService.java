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

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerFacade customerFacade, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerFacade = customerFacade;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return customerFacade.toResponse(customer);  // Перетворюємо Customer на CustomerResponse
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerFacade.toEntity(customerRequest);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
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

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


}
