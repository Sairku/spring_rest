package com.example.spring_rest.service;

import com.example.spring_rest.model.Customer;
import com.example.spring_rest.model.Account;
import com.example.spring_rest.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import com.example.spring_rest.repository.AccountRepository;

import java.util.List;

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
        Customer existingCustomer = customerRepository.getOne(id);
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setAge(updatedCustomer.getAge());
        return customerRepository.save(existingCustomer);
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
