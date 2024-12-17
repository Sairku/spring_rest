package controller;

import model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 1. Поповнити рахунок
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сума повинна бути більшою за 0");
        }
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok("Рахунок поповнено на " + amount);
    }

    // 2. Зняти гроші з рахунку
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сума повинна бути більшою за 0");
        }
        try {
            accountService.withdraw(accountNumber, amount);
            return ResponseEntity.ok("З рахунку знято " + amount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Переказати гроші на інший рахунок
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Сума повинна бути більшою за 0");
        }
        try {
            accountService.transfer(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok("Переказано " + amount + " з рахунку " + fromAccountNumber + " на рахунок " + toAccountNumber);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
