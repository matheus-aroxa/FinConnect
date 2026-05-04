package com.finconnect.account_service.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finconnect.account_service.dto.AccountResponse;
import com.finconnect.account_service.dto.CreateAccount;
import com.finconnect.account_service.dto.CreditAccountRequest;
import com.finconnect.account_service.dto.DebtFromAccountRequest;
import com.finconnect.account_service.entity.Account;
import com.finconnect.account_service.entity.Status;
import com.finconnect.account_service.exception.exceptions.AccountNotFoundException;
import com.finconnect.account_service.exception.exceptions.InsufficientBalanceException;
import com.finconnect.account_service.repository.AccountRepository;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    
    @Autowired
    private AccountRepository repository;

    public AccountResponse createAccountOnSignUp(CreateAccount request) {
        Account account = new Account();
        account.setCpf(request.cpf());
        account.setAgency(generateRandomAgencyNumber());
        account.setAccountNumber(generateRandomAgencyNumber());
        account.setBalance(new BigDecimal("0.0"));
        account.setStatus(Status.ACTIVE);

        var acc = this.repository.save(account);

        return new AccountResponse(
            acc.getCpf(),
            acc.getAgency(),
            acc.getAccountNumber(),
            acc.getBalance(),
            acc.getStatus()
        );
    }

    private String generateRandomAgencyNumber() {
        Random rand = new Random();
        int number = rand.nextInt(10000);
        return String.format("%04d", number);
    }

    public Account debitAmountFromAccount(DebtFromAccountRequest request) throws InsufficientBalanceException {
        Account account = findAccountByCpf(request.cpf()).orElseThrow(() -> new AccountNotFoundException());

        if(account.getBalance().compareTo(request.amount()) == -1) {
            logger.error("Insufficient balance");
            throw new InsufficientBalanceException();
        }

        logger.info("Debiting amount from account");
        account.setBalance(account.getBalance().subtract(request.amount()));

        return this.repository.save(account);
    }

    public Account creditAccount(CreditAccountRequest request) {
        Account account = findAccountByCpf(request.cpf()).orElseThrow(() -> new AccountNotFoundException());

        logger.info("Crediting amount to account");
        account.setBalance(account.getBalance().add(request.amount()));

        return this.repository.save(account);
    }

    public Optional<Account> findAccountByCpf(String cpf) {
        logger.info("Searching for account based on cpf");
        return this.repository.findAccountByCpf(cpf);
    }
}
