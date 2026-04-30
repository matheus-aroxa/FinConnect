package com.finconnect.auth_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.finconnect.auth_service.dto.AccountResponse;
import com.finconnect.auth_service.dto.CreateAccount;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    @PostMapping("/api/accounts/internal/create")
    ResponseEntity<AccountResponse> createAccountOnSignUp(@RequestBody CreateAccount request);
}
