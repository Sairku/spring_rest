package com.example.spring_rest.controller;

import com.example.spring_rest.dto.AccountRequest;
import com.example.spring_rest.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.spring_rest.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        log.info("Creating account with request: {}", accountRequest);
        AccountResponse accountResponse = accountService.createAccount(accountRequest);
        return ResponseEntity.status(201).body(accountResponse);
    }

    // 1. Поповнити рахунок
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestParam double amount) {
        log.info("Depositing {} to account {}", amount, accountNumber);
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount should be greater than 0");
        }
        AccountResponse accountResponse = accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok("Рахунок поповнено на " + amount);
    }

    // 2. Зняти гроші з рахунку
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestParam double amount) {
        log.info("Withdrawing {} from account {}", amount, accountNumber);
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount should be greater than 0");
        }
        try {
            accountService.withdraw(accountNumber, amount);
            return ResponseEntity.ok("З рахунку знято " + amount);
        } catch (IllegalArgumentException e) {
            log.warn("Withdrawal failed for account {}: {}", accountNumber, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Переказати гроші на інший рахунок
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam double amount) {
        log.info("Transferring {} from account {} to account {}", amount, fromAccountNumber, toAccountNumber);
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount should be greater than 0");
        }
        try {
            accountService.transfer(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok("Transfer " + amount + " from account " + fromAccountNumber + " to account " + toAccountNumber);
        } catch (IllegalArgumentException e) {
            log.warn("Transfer failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
