package com.finconnect.transaction_service.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import com.finconnect.transaction_service.entity.Status;
import com.finconnect.transaction_service.entity.Type;

public record TransactionResponse(
    UUID id,
    String originCpf,
    String destinationCpf,
    BigDecimal amount,
    Date createdAt,
    Type transactionType,
    Status transactionStatus
) {}
