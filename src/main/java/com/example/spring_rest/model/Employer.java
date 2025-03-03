package com.example.spring_rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employer extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @ManyToMany(mappedBy = "employers")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>(); // Багато клієнтів можуть працювати в одній компанії
}
