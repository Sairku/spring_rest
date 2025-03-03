package com.example.spring_rest.dto;

import com.example.spring_rest.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerFacade {

    @Autowired
    private ModelMapper modelMapper;

    public CustomerResponse toResponse(Customer customer) {
        return modelMapper.map(customer, CustomerResponse.class);
    }

    public Customer toEntity(CustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }
}

