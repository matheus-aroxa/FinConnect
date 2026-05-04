package com.finconnect.auth_service.dto;

import java.math.BigDecimal;
import com.finconnect.auth_service.feign.Status;

public record AccountResponse (
    String agency,
    String accountNumber,
    BigDecimal balance,
    Status status
) {}
