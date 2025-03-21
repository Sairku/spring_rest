package com.example.spring_rest.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequest {
    private long id;
    private String number;
    private String currency;
    @Min(0)
    private double balance;
    private double amount;

}
