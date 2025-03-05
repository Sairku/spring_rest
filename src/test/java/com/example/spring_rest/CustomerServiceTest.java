package com.example.spring_rest;
import com.example.spring_rest.dto.CustomerRequest;
import com.example.spring_rest.dto.CustomerResponse;
import com.example.spring_rest.model.Customer;
import com.example.spring_rest.repository.CustomerRepository;
import com.example.spring_rest.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        // Підготовка тестових даних
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPassword("encodedPassword");

        // Налаштування поведінки моків
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Виклик методу, який тестуємо
        CustomerResponse response = customerService.createCustomer(request);

        // Перевірка результатів
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());

        // Перевірка, що метод save був викликаний
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}

