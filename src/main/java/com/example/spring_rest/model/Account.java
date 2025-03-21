package com.example.spring_rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends AbstractEntity {

    @UuidGenerator
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(columnDefinition = "double default 0")
    private double balance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer; // Рахунок належить клієнту

    public Account(Currency currency, Customer customer) {
        this.currency = currency;
        this.customer = customer;
    }
}
