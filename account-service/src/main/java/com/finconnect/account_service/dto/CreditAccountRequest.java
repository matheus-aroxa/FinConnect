package com.finconnect.account_service.dto;

import java.math.BigDecimal;

public record CreditAccountRequest(
    String cpf,
    BigDecimal amount
) {}