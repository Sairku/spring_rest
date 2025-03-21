package com.example.spring_rest.controller;

import com.example.spring_rest.dto.AccountResponse;
import com.example.spring_rest.model.Currency;
import com.example.spring_rest.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;
    private AccountResponse accountResponse;

    private final String number = "1234567890";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        accountResponse = new AccountResponse(1L, "1234567890", Currency.USD, 0.0, 1L);
    }

    @Test
    void increaseAccountSuccess() throws Exception {
        int amount = 50;
        double expectedBalance = accountResponse.getBalance() + amount;

        accountResponse.setBalance(expectedBalance);

        when(accountService.deposit(number, amount)).thenReturn(accountResponse);

        mockMvc.perform(post("/accounts/{number}/deposit", number)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk());

    }

    @Test
    void increaseAccountBadRequest() throws Exception {
        int amount = -50;

        mockMvc.perform(post("/accounts/{number}/deposit", number)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Amount should be greater than 0"));
    }



    @Test
    void decreaseAccountBadRequest() throws Exception {
        int amount = -50;

        mockMvc.perform(post("/accounts/{number}/withdraw", number)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Amount should be greater than 0"));
    }

    @Test
    void transferSuccess() throws Exception {
        double amount = 50;
        String fromAccountNumber = "1234567890";
        String toAccountNumber = "987654321";

        doNothing().when(accountService).transfer(fromAccountNumber, toAccountNumber, amount);

        mockMvc.perform(post("/accounts/transfer")
                .param("fromAccountNumber", fromAccountNumber)
                .param("toAccountNumber", toAccountNumber)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer " + amount + " from account " + fromAccountNumber + " to account " + toAccountNumber));
    }

    @Test
    void transferBadRequest() throws Exception {
        double amount = -50;
        String fromAccountNumber = "1234567890";
        String toAccountNumber = "987654321";

        mockMvc.perform(post("/accounts/transfer")
                        .param("fromAccountNumber", fromAccountNumber)
                        .param("toAccountNumber", toAccountNumber)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Amount should be greater than 0"));
    }
}

