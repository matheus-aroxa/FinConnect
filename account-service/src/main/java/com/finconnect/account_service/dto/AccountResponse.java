package com.finconnect.account_service.dto;

import java.math.BigDecimal;
import com.finconnect.account_service.entity.Status;

public record AccountResponse (
    String cpf,
    String agency,
    String accountNumber,
    BigDecimal balance,
    Status status
) {}
