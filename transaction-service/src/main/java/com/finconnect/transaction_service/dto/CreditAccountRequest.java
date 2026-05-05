package com.finconnect.transaction_service.dto;

import java.math.BigDecimal;

public record CreditAccountRequest(
    String cpf,
    BigDecimal amount
) {}