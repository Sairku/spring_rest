package com.example.spring_rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Customer extends AbstractEntity {

    private String name;

    @ManyToMany
    private Set<Employer> employers; // Клієнт може працювати в кількох компаніях

    @Column(nullable = false, unique = true) // Додаємо анотацію для email
    private String email;

    @OneToMany(mappedBy = "customer")
    private Set<Account> accounts; // Зв'язок з рахунками

    // Гетери та сетери
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employer> getEmployers() {
        return employers;
    }

    public void setEmployers(Set<Employer> employers) {
        this.employers = employers;
    }
    public Set<Account> getAccounts() {
        return accounts; // Гетер для рахунків
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts; // Сеттер для рахунків
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
