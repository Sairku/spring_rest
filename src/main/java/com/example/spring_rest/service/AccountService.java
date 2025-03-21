package com.example.spring_rest.service;

import com.example.spring_rest.dto.AccountFacade;
import com.example.spring_rest.dto.AccountRequest;
import com.example.spring_rest.dto.AccountResponse;
import com.example.spring_rest.model.Account;
import com.example.spring_rest.repository.AccountRepository;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountFacade accountFacade;
    private final WebSocketService webSocketService;

    public AccountService(AccountRepository accountRepository, AccountFacade accountFacade, WebSocketService webSocketService) {
        this.accountRepository = accountRepository;
        this.accountFacade = accountFacade;
        this.webSocketService = webSocketService;
    }
    public AccountResponse createAccount(AccountRequest accountRequest) {

        Account account = accountFacade.toEntity(accountRequest);
        accountRepository.save(account);

        return accountFacade.toResponse(account);
    }

    public AccountResponse deposit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Рахунок не знайдено: " + accountNumber));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        webSocketService.sendAccountUpdate(accountNumber, "Рахунок поповнено на " + amount);
        return accountFacade.toResponse(account);
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Рахунок не знайдено: " + accountNumber));
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Недостатньо коштів на рахунку");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        webSocketService.sendAccountUpdate(accountNumber, "З рахунку знято " + amount);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Відправник не знайдений: " + fromAccountNumber));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Отримувач не знайдений: " + toAccountNumber));
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Недостатньо коштів на рахунку");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
