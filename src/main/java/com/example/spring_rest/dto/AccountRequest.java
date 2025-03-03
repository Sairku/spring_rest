package com.example.spring_rest.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AccountRequest {
    private long id;
    private String number;
    private String currency;
    @Min(0)
    private double balance;
    private double amount;

}
