package com.finconnect.account_service.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to debt from account")
public record DebtFromAccountRequest(

    @Schema(
        description = "User's cpf without symbols",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String cpf,

    @Schema(
        description = "Amount to debt from account",
        example = "20.0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal amount
) {}
