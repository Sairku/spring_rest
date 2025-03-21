package com.example.spring_rest.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.spring_rest.dto.AccountResponse;
import com.example.spring_rest.dto.CustomerRequest;
import com.example.spring_rest.dto.CustomerResponse;
import com.example.spring_rest.model.Currency;
import com.example.spring_rest.service.AccountService;
import com.example.spring_rest.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private CustomerResponse customerResponse;
    private CustomerRequest customerRequest;
    private AccountResponse accountResponse;

    private final String email = "john.doe@example.com";
    private final String name = "John";
    private final int age = 30;
    private final String phone = "1234567890";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        customerResponse = new CustomerResponse(1, name, email, age, phone, new ArrayList<>(), new HashSet<>());

        customerRequest = new CustomerRequest();
        customerRequest.setEmail(email);
        customerRequest.setPassword("Password123!");
        customerRequest.setName(name);
        customerRequest.setAge(age);
        customerRequest.setPhone(phone);

        accountResponse = new AccountResponse(1L, "1234567890", Currency.USD, 0.0, 1L);
    }

    @Test
    void testGetById() throws Exception {
        long customerId = 1;

        when(customerService.getCustomerById(customerId)).thenReturn(customerResponse);

        mockMvc.perform(get("/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.phone").value(phone));

        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    void testCreateSuccess() throws Exception {
        when(customerService.createCustomer(customerRequest)).thenReturn(customerResponse);

        mockMvc.perform(post("/customers")
                        .contentType("application/json")
                        .content("{\"email\":\"" + email + "\", \"password\":\"Password123!\", \"name\":\"" + name + "\", \"age\":" + age + ", \"phone\":\"" + phone + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.accounts").isEmpty())
                .andExpect(jsonPath("$.employers").isEmpty());

        verify(customerService, times(1)).createCustomer(any(CustomerRequest.class));
    }

    @Test
    void testCreateBadRequest() throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"\", \"password\":\"\", \"name\":\"\", \"age\":0, \"phone\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(customerService, never()).createCustomer(any(CustomerRequest.class));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        long customerId = 1L;
        CustomerRequest updateRequest = new CustomerRequest(1, "newEmail@gmail.com", "NewPassword1!", "Alex", 35, "16124040290");

        customerResponse.setEmail(updateRequest.getEmail());
        customerResponse.setName(updateRequest.getName());
        customerResponse.setAge(updateRequest.getAge());
        customerResponse.setPhone(updateRequest.getPhone());

        when(customerService.updateCustomer(eq(customerId), any(CustomerRequest.class))).thenReturn(customerResponse);

        mockMvc.perform(put("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(updateRequest.getEmail()))
                .andExpect(jsonPath("$.name").value(updateRequest.getName()));

        verify(customerService, times(1)).updateCustomer(eq(customerId), any(CustomerRequest.class));
    }

    @Test
    void testUpdateInvalidPhone() throws Exception {
        long customerId = 1L;

        customerRequest.setPassword("0930744530");

        mockMvc.perform(put("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete() throws Exception {
        long customerId = 1L;

        mockMvc.perform(delete("/customers/{id}", customerId))
                .andExpect(status().isOk())

                .andExpect(content().string("Customer deleted"));

        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}
