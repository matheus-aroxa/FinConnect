package com.finconnect.account_service.controller.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.finconnect.account_service.dto.AccountInfoResponse;
import com.finconnect.account_service.dto.AccountResponse;
import com.finconnect.account_service.dto.CreateAccount;
import com.finconnect.account_service.dto.CreditAccountRequest;
import com.finconnect.account_service.dto.DebtFromAccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface AccountControllerDoc {
    
    @Operation(summary = "Creates a bank account", description = "Internal endpoint called on sign up to create the user's account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<AccountResponse> createAccountOnSignUp(@RequestBody CreateAccount request);
    //--------------------------------//-----------------------------------//-----------------------------------------------//
    @Operation(summary = "Debt amount from account", description = "Debt amount from account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Debted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<AccountResponse> debitAmountFromAccount(@RequestBody DebtFromAccountRequest request) throws Exception;
    //--------------------------------//-----------------------------------//-----------------------------------------------//
    @Operation(summary = "Credit amount into account", description = "Credit amount into account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Credited successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<AccountResponse> creditAccount(@RequestBody CreditAccountRequest request);
    //--------------------------------//-----------------------------------//-----------------------------------------------//
    @Operation(summary = "Get account info", description = "Get account info based on cpf")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Got info successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<AccountInfoResponse> accountInfo(@PathVariable String cpf);
}
