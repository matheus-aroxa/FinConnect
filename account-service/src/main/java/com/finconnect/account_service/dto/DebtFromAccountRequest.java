package com.finconnect.account_service.dto;

import java.math.BigDecimal;

public record DebtFromAccountRequest(
    String cpf,
    BigDecimal amount
) {}
