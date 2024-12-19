package com.example.spring_rest.reposirory;

import com.example.spring_rest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
