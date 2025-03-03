package com.example.spring_rest.dto;

import com.example.spring_rest.model.Employer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployerFacade {

    @Autowired
    private ModelMapper modelMapper;

    public EmployerResponse toResponse(Employer employer) {
        return modelMapper.map(employer, EmployerResponse.class);
    }

    public Employer toEntity(EmployerRequest employerRequest) {
        return modelMapper.map(employerRequest, Employer.class);
    }
}
