package com.example.spring_rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private long id;

    @Email
    @NotNull
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Пароль повинен містити принаймні одну велику літеру, цифру і спеціальний символ")
    private String password;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]{2,}$")  // Валідація імені
    private String name;

    @Min(18)  // Валідація віку
    private int age;

    @Pattern(regexp = "^\\+?[0-9]{1,3}?[-.\\s]?\\(?[0-9]{1,3}?\\)?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$")  // Валідація телефону
    private String phone;
}
