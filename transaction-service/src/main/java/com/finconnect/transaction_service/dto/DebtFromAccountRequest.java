package com.finconnect.transaction_service.dto;

import java.math.BigDecimal;

public record DebtFromAccountRequest(
    String cpf,
    BigDecimal amount
) {}
