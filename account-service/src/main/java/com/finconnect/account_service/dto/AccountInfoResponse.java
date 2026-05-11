package com.finconnect.account_service.dto;

import java.math.BigDecimal;

public record AccountInfoResponse(
    String accountNumber,
    String agency,
    BigDecimal balance
) {}