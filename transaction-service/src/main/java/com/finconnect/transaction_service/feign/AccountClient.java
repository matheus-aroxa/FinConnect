package com.finconnect.transaction_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.finconnect.transaction_service.configuration.FeignConfig;
import com.finconnect.transaction_service.dto.AccountResponse;
import com.finconnect.transaction_service.dto.CreditAccountRequest;
import com.finconnect.transaction_service.dto.DebtFromAccountRequest;

@FeignClient(name = "ACCOUNT-SERVICE", configuration = FeignConfig.class)
public interface AccountClient {

    @PostMapping("api/accounts/debt")
    public ResponseEntity<AccountResponse> debitAmountFromAccount(@RequestBody DebtFromAccountRequest request);

    @PostMapping("api/accounts/credit")
    public ResponseEntity<AccountResponse> creditAccount(@RequestBody CreditAccountRequest request);
}
