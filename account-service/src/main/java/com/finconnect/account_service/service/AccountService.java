package com.finconnect.account_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finconnect.account_service.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository repository;
}
