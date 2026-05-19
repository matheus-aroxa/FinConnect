package com.finconnect.account_service.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to credit into account")
public record CreditAccountRequest(

    @Schema(
        description = "User's cpf without symbols",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String cpf,

    @Schema(
        description = "Amount to credit into account",
        example = "30.0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal amount
) {}