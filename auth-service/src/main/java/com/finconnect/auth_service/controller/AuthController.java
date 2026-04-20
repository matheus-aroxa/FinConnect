package com.finconnect.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    @GetMapping("login")
    public String authenticateUser() {
        return "hello";
    }

}
