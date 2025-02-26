package com.example.spring_rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "employer")
public class Employer extends AbstractEntity {

    private String name;
    private String address;

    @ManyToMany(mappedBy = "employers")
    private Set<Customer> customers; // Багато клієнтів можуть працювати в одній компанії

    // Гетери та сетери
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}
