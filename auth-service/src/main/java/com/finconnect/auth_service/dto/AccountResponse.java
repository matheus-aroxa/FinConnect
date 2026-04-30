package com.finconnect.auth_service.dto;

import java.math.BigDecimal;
import java.util.UUID;
import com.finconnect.auth_service.feign.Status;

public record AccountResponse (
    UUID id,
    UUID userId,
    String agency,
    String accountNumber,
    BigDecimal balance,
    Status status
) {}
