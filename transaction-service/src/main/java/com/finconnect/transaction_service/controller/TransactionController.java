package com.finconnect.transaction_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.finconnect.transaction_service.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.finconnect.transaction_service.controller.docs.TransactionControllerDoc;
import com.finconnect.transaction_service.dto.StatementResponse;
import com.finconnect.transaction_service.dto.TransferRequest;

@Tag(name = "Transactions", description = "Transactions API")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController implements TransactionControllerDoc {

    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(this.transactionService.transfer(request));
    }

    @GetMapping("/statement/{cpf}")
    public ResponseEntity<StatementResponse> getStatement(@PathVariable String cpf) {
        return ResponseEntity.ok(this.transactionService.getStatement(cpf));
    }
}
