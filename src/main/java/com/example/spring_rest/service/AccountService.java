package com.example.spring_rest.service;

import com.example.spring_rest.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Рахунок не знайдено: " + accountNumber));
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Рахунок не знайдено: " + accountNumber));
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Недостатньо коштів на рахунку");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accountRepository.findByNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Відправник не знайдений: " + fromAccountNumber));
        Account toAccount = accountRepository.findByNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Отримувач не знайдений: " + toAccountNumber));
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Недостатньо коштів на рахунку");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
    public interface AccountRepository extends JpaRepository<Account, Long> {
        Optional<Account> findByNumber(String number);
    }
}
