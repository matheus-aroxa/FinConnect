package com.finconnect.transaction_service.controller.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.finconnect.transaction_service.dto.StatementResponse;
import com.finconnect.transaction_service.dto.TransferRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface TransactionControllerDoc {

    @Operation(summary = "Transfer amount between accounts", description = "Transfer amount between accounts")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Amount transfered successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request);
    //-------------------------------//-------------------------------//---------------------------------//----------------------//
    @Operation(summary = "Get account statement", description = "Get the transactions history")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Got statement successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<StatementResponse> getStatement(@PathVariable String cpf);
}
