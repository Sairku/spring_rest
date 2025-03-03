package com.example.spring_rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {

    private Long id;
    private Double balance;
    private Long customerId;
}
