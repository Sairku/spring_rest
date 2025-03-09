package com.example.spring_rest;

import com.example.spring_rest.controller.AccountController;
import com.example.spring_rest.service.AccountService;
import com.example.spring_rest.dto.AccountRequest;
import com.example.spring_rest.dto.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @Test
    public void testCreateAccount() throws Exception {
        // Підготовка тестових даних
        AccountRequest request = new AccountRequest();
        request.setNumber("12345");
        request.setCurrency("USD");
        request.setBalance(1000.0);

        AccountResponse response = new AccountResponse();
        response.setId(1L);
        response.setBalance(1000.0);

        when(accountService.createAccount(any(AccountRequest.class))).thenReturn(response);

        // Тестування запиту
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())  // Перевірка статусу
                .andExpect(jsonPath("$.balance").value(1000.0));  // Перевірка значень у відповіді
    }
}
