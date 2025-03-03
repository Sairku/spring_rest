package com.example.spring_rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private long id;

    @Min(18)  // Валідація віку
    private int age;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]{2,}$")  // Валідація імені
    private String name;

    @Email
    @NotNull
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{1,3}?[-.\\s]?\\(?[0-9]{1,3}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$")  // Валідація телефону
    private String phone;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Пароль повинен містити принаймні одну велику літеру, цифру і спеціальний символ")
    private String password;
}
