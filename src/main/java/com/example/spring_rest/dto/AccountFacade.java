package com.example.spring_rest.dto;

import com.example.spring_rest.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFacade {
    private  final ModelMapper modelMapper;
    @Autowired
    public AccountFacade(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountResponse toResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }

    public Account toEntity(AccountRequest accountRequest) {
        return modelMapper.map(accountRequest, Account.class);
    }
}
