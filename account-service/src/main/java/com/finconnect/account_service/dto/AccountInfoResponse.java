package com.finconnect.account_service.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Account info")
public record AccountInfoResponse(

    @Schema(
        description = "User's account number",
        example = "00000-1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String accountNumber,

    @Schema(
        description = "User's account agency",
        example = "00000-1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String agency,

    @Schema(
        description = "Account balance",
        example = "0.0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal balance
) {}