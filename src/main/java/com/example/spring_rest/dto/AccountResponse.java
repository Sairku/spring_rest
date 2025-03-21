package com.example.spring_rest.dto;

import com.example.spring_rest.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private Currency currency;
    private double balance;
    private Long customerId;

}
