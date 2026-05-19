package com.finconnect.auth_service.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finconnect.auth_service.controller.docs.AuthControllerDocs;
import com.finconnect.auth_service.dto.AccountResponse;
import com.finconnect.auth_service.dto.EmailFromCpfRequest;
import com.finconnect.auth_service.dto.EmailFromCpfResponse;
import com.finconnect.auth_service.dto.RefreshRequest;
import com.finconnect.auth_service.dto.SignInRequest;
import com.finconnect.auth_service.dto.SignInResponse;
import com.finconnect.auth_service.dto.SignUpRequest;
import com.finconnect.auth_service.dto.UserInfoFromJwtResponse;
import com.finconnect.auth_service.dto.UserInfoRequest;
import com.finconnect.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthService authService;    
    
    @PostMapping("/signin")
    public SignInResponse authenticateUser(@Valid @RequestBody SignInRequest request) {
        return this.authService.authenticateUser(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<AccountResponse> registerUser(@Valid @RequestBody SignUpRequest request) throws BadRequestException {
        return this.authService.registerUser(request);
    }

    @PostMapping("/me")
    public ResponseEntity<UserInfoFromJwtResponse> me(@RequestBody UserInfoRequest request) {
        return ResponseEntity.ok(this.authService.me(request));
    }

    @PostMapping("/email")
    public ResponseEntity<EmailFromCpfResponse> findEmailFromCpf(@RequestBody EmailFromCpfRequest request) {
        return ResponseEntity.ok(this.authService.findEmailFromCpf(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(this.authService.refresh(request));
    }
}
