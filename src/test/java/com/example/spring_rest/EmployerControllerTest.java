package com.example.spring_rest;

import com.example.spring_rest.controller.EmployerController;
import com.example.spring_rest.service.EmployerService;
import com.example.spring_rest.dto.EmployerRequest;
import com.example.spring_rest.dto.EmployerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployerController.class)
public class EmployerControllerTest {

    @Mock
    private EmployerService employerService;

    @InjectMocks
    private EmployerController employerController;

    private MockMvc mockMvc;

    @Test
    public void testCreateEmployer() throws Exception {
        // Підготовка тестових даних
        EmployerRequest request = new EmployerRequest();
        request.setName("Tech Corp");
        request.setAddress("123 Tech Street");

        EmployerResponse response = new EmployerResponse();
        response.setName("Tech Corp");
        response.setAddress("123 Tech Street");

        when(employerService.createEmployer(any(EmployerRequest.class))).thenReturn(response);

        // Тестування запиту
        mockMvc.perform(post("/employers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())  // Перевірка статусу
                .andExpect(jsonPath("$.name").value("Tech Corp"))
                .andExpect(jsonPath("$.address").value("123 Tech Street"));  // Перевірка значення
    }
}
