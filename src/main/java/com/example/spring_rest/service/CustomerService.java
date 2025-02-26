package com.example.spring_rest.service;

import com.example.spring_rest.model.Customer;
import com.example.spring_rest.model.Account;
import com.example.spring_rest.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import com.example.spring_rest.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;


    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.getOne(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        // Перевіряємо, чи існує клієнт з таким ID
        Optional<Customer> existingCustomerOpt = customerRepository.findById(id);

        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();

            // Оновлюємо дані клієнта
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail()); // Якщо є поле email
            existingCustomer.setEmployers(updatedCustomer.getEmployers()); // Якщо потрібно оновити список роботодавців

            // Зберігаємо оновленого клієнта
            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id); // Якщо клієнт не знайдений
        }
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Account createAccountForCustomer(Long customerId, Account account) {
        Customer customer = customerRepository.getOne(customerId);
        account.setCustomer(customer);
        Account savedAccount = accountRepository.save(account);
        customer.getAccounts().add(savedAccount);
        customerRepository.save(customer);
        return savedAccount;
    }

    public void deleteAccountForCustomer(Long customerId, Long accountId) {
        Customer customer = customerRepository.getOne(customerId);
        Account accountToRemove = accountRepository.getOne(accountId);
        customer.getAccounts().remove(accountToRemove);
        accountRepository.delete(accountToRemove);
    }

}
