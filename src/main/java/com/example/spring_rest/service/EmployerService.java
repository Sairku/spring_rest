package com.example.spring_rest.service;

import com.example.spring_rest.dto.EmployerFacade;
import com.example.spring_rest.dto.EmployerRequest;
import com.example.spring_rest.dto.EmployerResponse;
import com.example.spring_rest.model.Employer;
import com.example.spring_rest.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;
    private final EmployerFacade employerFacade;

    public EmployerResponse createEmployer(EmployerRequest employerRequest) {
        // Перетворюємо EmployerRequest на сутність Employer
        Employer employer = employerFacade.toEntity(employerRequest);
        employerRepository.save(employer);
        // Перетворюємо Employer назад в EmployerResponse для відповіді
        return employerFacade.toResponse(employer);
    }

    public EmployerResponse getEmployerById(Long id) {
        Employer employer = employerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employer not found"));
        return employerFacade.toResponse(employer);
    }

    public Employer save(Employer employer) {
        return employerRepository.save(employer);
    }

    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}



