package com.finconnect.transaction_service.dto;

import java.math.BigDecimal;

public record TransferRequest(
    String origin, //cpf
    String destination, //cpf
    BigDecimal amount
) {}
