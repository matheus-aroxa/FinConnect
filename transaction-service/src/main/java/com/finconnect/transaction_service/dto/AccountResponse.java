package com.finconnect.transaction_service.dto;

import java.math.BigDecimal;
import com.finconnect.transaction_service.entity.AccountStatus;

public record AccountResponse (
    String cpf,
    String agency,
    String accountNumber,
    BigDecimal balance,
    AccountStatus status
) {}
