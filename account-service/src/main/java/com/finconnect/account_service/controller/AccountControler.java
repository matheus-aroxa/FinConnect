package com.finconnect.account_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finconnect.account_service.dto.CreateAccount;
import com.finconnect.account_service.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountControler {

    @Autowired
    private AccountService service;
    
    @PostMapping("/internal/create")
    public void create(@RequestBody CreateAccount request) {
        this.service.createAccount(request);
    }

    @GetMapping("/me")
    public void me() {

    }

    @PatchMapping("/balance/update")
    public void update() {

    }
}