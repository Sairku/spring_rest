package controller;

import model.Customer;
import model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 1. Отримати інформацію про окремого користувача, включаючи його рахунки
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // 2. Отримати інформацію про всіх користувачів
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // 3. Створити користувача
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    // 4. Змінити дані користувача
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        return ResponseEntity.ok(customer);
    }

    // 5. Видалити користувача
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Користувача видалено");
    }

    // 6. Створити рахунок для конкретного користувача
    @PostMapping("/{id}/accounts")
    public ResponseEntity<Account> createAccountForCustomer(@PathVariable Long id, @RequestBody Account account) {
        Account createdAccount = customerService.createAccountForCustomer(id, account);
        return ResponseEntity.ok(createdAccount);
    }

    // 7. Видалити рахунок у користувача
    @DeleteMapping("/{id}/accounts/{accountId}")
    public ResponseEntity<String> deleteAccountForCustomer(@PathVariable Long id, @PathVariable Long accountId) {
        customerService.deleteAccountForCustomer(id, accountId);
        return ResponseEntity.ok("Рахунок видалено");
    }
}
