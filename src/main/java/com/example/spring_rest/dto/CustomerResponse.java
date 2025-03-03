package com.example.spring_rest.dto;

import com.example.spring_rest.model.Account;
import com.example.spring_rest.model.Employer;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private long id;
    private String name;
    private String email;
    private int age;
    private String phone;
    private List<Account> accounts;
    private Set<Employer> employers;
}
