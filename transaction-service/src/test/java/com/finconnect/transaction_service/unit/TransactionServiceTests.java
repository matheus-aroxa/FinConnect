package com.finconnect.transaction_service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.finconnect.transaction_service.dto.EmailFromCpfResponse;
import com.finconnect.transaction_service.dto.TransferRequest;
import com.finconnect.transaction_service.entity.Status;
import com.finconnect.transaction_service.feign.AccountClient;
import com.finconnect.transaction_service.feign.AuthClient;
import com.finconnect.transaction_service.repository.TransactionRepository;
import com.finconnect.transaction_service.service.ReceiptProducerService;
import com.finconnect.transaction_service.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {
    
    @Mock private TransactionRepository transactionRepository;
    @Mock private AccountClient accountClient;
    @Mock private AuthClient authClient;
    @Mock private ReceiptProducerService receiptProducerService;

    @InjectMocks private TransactionService transactionService;

    @Test
    void shouldTransferSuccessfully() {
        TransferRequest request = new TransferRequest("111", "222", new BigDecimal("100.00"));

        when(authClient.findEmailFromCpf(any())).thenReturn(ResponseEntity.ok(new EmailFromCpfResponse("email@test.com")));

        String result = transactionService.transfer(request);

        assertEquals("Transaction completed", result);
        verify(accountClient).debitAmountFromAccount(any());
        verify(accountClient).creditAccount(any());
        verify(transactionRepository).save(argThat(t -> t.getTransactionStatus() == Status.COMPLETED));
        verify(receiptProducerService, times(2)).sendMessage(any());
    }

    @Test
    void shouldThrowExceptionWhenDebitFails() {
        TransferRequest request = new TransferRequest("111", "222", new BigDecimal("100.00"));

        when(accountClient.debitAmountFromAccount(any())).thenThrow(new RuntimeException("Insufficient funds"));

        String result = transactionService.transfer(request);

        assertEquals("Transaction failed", result);
        verify(transactionRepository).save(argThat(t -> t.getTransactionStatus() == Status.FAILED));
        verify(accountClient, never()).creditAccount(any());
    }
}
