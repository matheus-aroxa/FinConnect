package com.finconnect.auth_service.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finconnect.auth_service.dto.AccountResponse;
import com.finconnect.auth_service.dto.SignInRequest;
import com.finconnect.auth_service.dto.SignUpRequest;
import com.finconnect.auth_service.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;    
    
    @PostMapping("/signin")
    public String authenticateUser(@Valid @RequestBody SignInRequest request) {
        return this.authService.authenticateUser(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<AccountResponse> registerUser(@Valid @RequestBody SignUpRequest request) throws BadRequestException {
        return this.authService.registerUser(request);
    }
}
