package com.example.spring_rest.service;

import com.example.spring_rest.dto.AccountFacade;
import com.example.spring_rest.dto.AccountRequest;
import com.example.spring_rest.dto.AccountResponse;
import com.example.spring_rest.exception.CustomerException;
import com.example.spring_rest.exception.NotFoundException;
import com.example.spring_rest.model.Account;
import com.example.spring_rest.model.Currency;
import com.example.spring_rest.model.Customer;
import com.example.spring_rest.repository.AccountRepository;
import com.example.spring_rest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private AccountService accountService;

    private AccountRequest accountRequest;
    private AccountResponse accountResponse;
    private Account account;

    private final String number = "1234567890";
    private final Currency currency = Currency.USD;

    @BeforeEach
    void setUp() {
        accountRequest = new AccountRequest(1L, number, currency.toString(), 0, 0);
        accountResponse = new AccountResponse(1L, number, currency, 0, 1L);

        account = new Account(number, currency, 0, null);
        account.setId(1L);
    }

    @Test
    void testCreateAccount() {

        when(accountFacade.toEntity(accountRequest)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountFacade.toResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.createAccount(accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);
    }


    @Test
    void testDepositSuccess() {
        double amount = 100.50;
        double expectedBalance = account.getBalance() + amount;

        when(accountRepository.findByAccountNumber(number)).thenReturn(Optional.of(account));

        account.setBalance(account.getBalance() + amount);
        when(accountRepository.save(account)).thenReturn(account);

        accountResponse.setBalance(account.getBalance());
        when(accountFacade.toResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.deposit(number, amount);

        assertNotNull(result);
        assertEquals(expectedBalance, result.getBalance());
    }

    @Test
    void testDepositAccountNotFound() {
        double amount = 100.50;

        when(accountRepository.findByAccountNumber(number)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(number, amount);
        });
    }

}
