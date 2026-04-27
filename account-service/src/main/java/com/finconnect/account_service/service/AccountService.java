package com.finconnect.account_service.service;

import java.math.BigDecimal;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finconnect.account_service.dto.CreateAccount;
import com.finconnect.account_service.entity.Account;
import com.finconnect.account_service.entity.Status;
import com.finconnect.account_service.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository repository;

    public void createAccount(CreateAccount request) {
        Account account = new Account();
        account.setUserId(request.uuid());
        account.setAgency(generateRandomAgencyNumber());
        account.setAccountNumber(generateRandomAgencyNumber());
        account.setBalance(new BigDecimal("0.0"));
        account.setStatus(Status.ACTIVE);

        this.repository.save(account);
    }

    private String generateRandomAgencyNumber() {
        Random rand = new Random();
        int number = rand.nextInt(10000);
        return String.format("%04d", number);
    }
}
