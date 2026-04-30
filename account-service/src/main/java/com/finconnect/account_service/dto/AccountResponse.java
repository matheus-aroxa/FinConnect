package com.finconnect.account_service.dto;

import java.math.BigDecimal;
import java.util.UUID;
import com.finconnect.account_service.entity.Status;

public record AccountResponse (
    UUID id,
    UUID userId,
    String agency,
    String accountNumber,
    BigDecimal balance,
    Status status
) {}
