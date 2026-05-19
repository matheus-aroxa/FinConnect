package com.finconnect.account_service.dto;

import java.math.BigDecimal;
import com.finconnect.account_service.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The account info")
public record AccountResponse (

    @Schema(
        description = "User's cpf without symbols",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String cpf,

    @Schema(
        description = "User's account agency",
        example = "00000-1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String agency,

    @Schema(
        description = "User's account",
        example = "00000-1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String accountNumber,

    @Schema(
        description = "User's account balance",
        example = "0.0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal balance,

    @Schema(
        description = "User's account status",
        example = "ACTIVE | BLOCKED",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    Status status
) {}
