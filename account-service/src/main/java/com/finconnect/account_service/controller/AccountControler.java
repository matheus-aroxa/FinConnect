package com.finconnect.account_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finconnect.account_service.controller.docs.AccountControllerDoc;
import com.finconnect.account_service.dto.AccountInfoResponse;
import com.finconnect.account_service.dto.AccountResponse;
import com.finconnect.account_service.dto.CreateAccount;
import com.finconnect.account_service.dto.CreditAccountRequest;
import com.finconnect.account_service.dto.DebtFromAccountRequest;
import com.finconnect.account_service.service.AccountService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Accounts", description = "Accounts API")
@RestController
@RequestMapping("/api/accounts")
public class AccountControler implements AccountControllerDoc {

    @Autowired
    private AccountService service;
    
    @PostMapping("/internal/create")
    public ResponseEntity<AccountResponse> createAccountOnSignUp(@RequestBody CreateAccount request) {
        return ResponseEntity.ok(this.service.createAccountOnSignUp(request));
    }

    @PostMapping("/debt")
    public ResponseEntity<AccountResponse> debitAmountFromAccount(@RequestBody DebtFromAccountRequest request) throws Exception {
        return ResponseEntity.ok(this.service.debitAmountFromAccount(request));
    }

    @PostMapping("/credit")
    public ResponseEntity<AccountResponse> creditAccount(@RequestBody CreditAccountRequest request) {
        return ResponseEntity.ok(this.service.creditAccount(request));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<AccountInfoResponse> accountInfo(@PathVariable String cpf) {
        return ResponseEntity.ok(this.service.getAccountInfo(cpf));
    }
}