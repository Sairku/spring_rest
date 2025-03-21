package com.example.spring_rest.service;

import com.example.spring_rest.dto.EmployerFacade;
import com.example.spring_rest.dto.EmployerRequest;
import com.example.spring_rest.dto.EmployerResponse;
import com.example.spring_rest.model.Employer;
import com.example.spring_rest.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final EmployerRepository employerRepository;
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

    public List<EmployerResponse> getAllEmployers() {
        return employerRepository.findAll()
                .stream()
                .map(employerFacade::toResponse)
                .toList();
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}



