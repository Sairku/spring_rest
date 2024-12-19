package com.example.spring_rest.reposirory;

import com.example.spring_rest.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}


