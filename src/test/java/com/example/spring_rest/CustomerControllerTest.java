package com.example.spring_rest.controller;

import com.example.spring_rest.dto.CustomerRequest;
import com.example.spring_rest.dto.CustomerResponse;
import com.example.spring_rest.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() throws Exception {
        // Створюємо DTO для запиту
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("password");

        // Створюємо DTO для відповіді
        CustomerResponse response = new CustomerResponse();
        response.setName("John Doe");
        response.setEmail("john@example.com");

        // Мокаємо виклик сервісу
        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(response);

        // Виконуємо тестовий запит
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())  // Перевірка статусу відповіді
                .andExpect(jsonPath("$.name").value("John Doe"))  // Перевірка значення поля name
                .andExpect(jsonPath("$.email").value("john@example.com"));  // Перевірка значення поля email

        // Перевіряємо, чи викликано метод сервісу
        verify(customerService, times(1)).createCustomer(request);
    }

    @Test
    public void testCreateCustomer_invalidEmail() throws Exception {
        // Створюємо DTO для запиту з некоректним email
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("invalid-email");
        request.setPassword("password");

        // Виконуємо запит і перевіряємо, що повернеться статус 400 та відповідне повідомлення
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())  // Перевірка, чи статус відповіді 400
                .andExpect(jsonPath("$.email").value("Email should be valid"));  // Перевірка повідомлення про помилку
    }

    @Test
    public void testCreateCustomer_emptyPassword() throws Exception {
        // Створюємо DTO для запиту з порожнім паролем
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPassword("");  // Порожній пароль

        // Виконуємо запит і перевіряємо, що повернеться статус 400 та відповідне повідомлення
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())  // Перевірка, чи статус відповіді 400
                .andExpect(jsonPath("$.password").value("Customer password is mandatory"));  // Перевірка повідомлення про помилку
    }
}
