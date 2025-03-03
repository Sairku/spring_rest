package com.example.spring_rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EmployerRequest {

    @NotNull
    @Min(3)  // Ім'я компанії має містити щонайменше 3 символи
    private String name;

    @NotNull
    @Min(3)  // Адреса компанії повинна містити щонайменше 3 символи
    private String address;
}
