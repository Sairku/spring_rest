package com.example.spring_rest.repository;

import com.example.spring_rest.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    public Employer findByName(String name);
}
