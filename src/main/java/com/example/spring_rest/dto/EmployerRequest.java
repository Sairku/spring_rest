package com.example.spring_rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployerRequest {

    @NotNull
    @Length(min=3)  // Ім'я компанії має містити щонайменше 3 символи
    private String name;

    @NotNull
    @Length(min=3)  // Адреса компанії повинна містити щонайменше 3 символи
    private String address;
}
