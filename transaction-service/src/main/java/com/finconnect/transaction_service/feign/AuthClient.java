package com.finconnect.transaction_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.finconnect.transaction_service.dto.EmailFromCpfRequest;
import com.finconnect.transaction_service.dto.EmailFromCpfResponse;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthClient {

    @PostMapping("/api/auth/email")
    public ResponseEntity<EmailFromCpfResponse> findEmailFromCpf(@RequestBody EmailFromCpfRequest request);
}
