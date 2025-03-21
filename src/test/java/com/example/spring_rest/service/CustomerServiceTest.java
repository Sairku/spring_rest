package com.example.spring_rest.service;

import com.example.spring_rest.dto.CustomerFacade;
import com.example.spring_rest.dto.CustomerRequest;
import com.example.spring_rest.dto.CustomerResponse;
import com.example.spring_rest.model.Customer;
import com.example.spring_rest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerFacade customerFacade;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private final String email = "john.doe@example.com";
    private final String name = "John Doe";

    private Customer customer;
    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        // Initialize mock data before each test
        customer = new Customer(email, "password123", name, 30, "1234567890", new ArrayList<>(), new HashSet<>());
        customerRequest = new CustomerRequest(1, email, "password123", "John Doe", 30, "1234567890");
        customerResponse = new CustomerResponse(1, name, email, 30, "1234567890", null, null);
    }

    @Test
    void testGetAll() {
        Pageable pageable = Pageable.unpaged();
        List<Customer> customers = List.of(customer);
        Page<Customer> customerPage = new PageImpl<>(customers);

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);
        when(customerFacade.toResponse(customer)).thenReturn(customerResponse);

        Page<CustomerResponse> result = customerService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());  // Verify there's exactly 1 customer in the response
        assertEquals(email, result.getContent().getFirst().getEmail());
        assertEquals(name, result.getContent().getFirst().getName());

        verify(customerRepository, times(1)).findAll(pageable);  // Verify findAll is called once
        verify(customerFacade, times(1)).toResponse(customer);  // Verify toResponse is called once for each customer
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerFacade.toResponse(customer)).thenReturn(customerResponse);

        CustomerResponse foundCustomer = customerService.getCustomerById(1L);

        assertNotNull(foundCustomer);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void testCreateCustomer() {
        when(customerFacade.toEntity(customerRequest)).thenReturn(customer);
        when(passwordEncoder.encode(customer.getPassword())).thenReturn("encodedPassword");
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerFacade.toResponse(customer)).thenReturn(customerResponse);

        CustomerResponse savedCustomer = customerService.createCustomer(customerRequest);

        assertNotNull(savedCustomer);
        assertEquals(email, savedCustomer.getEmail());
        assertEquals(name, savedCustomer.getName());

        verify(customerRepository, times(1)).save(customer); // Verify that save was called once
    }

    @Test
    void testUpdate() {

        CustomerRequest updateRequest = new CustomerRequest(1, "newEmail@gmail.com", "newPassword1!", "Pasha", 21, "380987654321");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customer.setEmail(updateRequest.getEmail());
        customer.setPassword(updateRequest.getPassword());
        customer.setName(updateRequest.getName());
        customer.setAge(updateRequest.getAge());
        customer.setPhone(updateRequest.getPhone());

        customerResponse.setEmail(updateRequest.getEmail());
        customerResponse.setName(updateRequest.getName());
        customerResponse.setAge(updateRequest.getAge());
        customerResponse.setPhone(updateRequest.getPhone());

        when(customerFacade.toEntity(any(CustomerRequest.class))).thenReturn(customer);  // Використовуємо any()
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerFacade.toResponse(customer)).thenReturn(customerResponse);

        CustomerResponse updatedCustomer = customerService.updateCustomer(1L, updateRequest);

        assertNotNull(updatedCustomer);
        assertEquals(updateRequest.getEmail(), updatedCustomer.getEmail());
        assertEquals(updateRequest.getName(), updatedCustomer.getName());
        assertEquals(updateRequest.getAge(), updatedCustomer.getAge());
        assertEquals(updateRequest.getPhone(), updatedCustomer.getPhone());
    }

    @Test
    void testUpdateNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.updateCustomer(1L, customerRequest));
    }
}