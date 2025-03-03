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
    private final CustomerService customerService;
    private final AccountFacade accountFacade;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountFacade accountFacade) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountFacade = accountFacade;
    }
    public AccountResponse createAccount(AccountRequest accountRequest) {
        // Перетворюємо AccountRequest на сутність Account
        Account account = accountFacade.toEntity(accountRequest);
        accountRepository.save(account);
        // Перетворюємо Account назад в AccountResponse для відповіді
        return accountFacade.toResponse(account);
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Рахунок не знайдено: " + accountNumber));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Рахунок не знайдено: " + accountNumber));
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Недостатньо коштів на рахунку");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
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
