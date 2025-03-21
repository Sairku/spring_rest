package com.example.spring_rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {
    @NotBlank(message = "User email is mandatory")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "User password is mandatory")
    private String password;

    @NotBlank(message = "User username is mandatory")
    private String username;
}
