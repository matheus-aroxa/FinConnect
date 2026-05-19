package com.finconnect.auth_service.dto;

import java.math.BigDecimal;
import com.finconnect.auth_service.feign.Status;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The account response returned in the sign up")
public record AccountResponse (

    @Schema(
        description = "user's account agency",
        example = "0000-1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String agency,

    @Schema(
        description = "user's account number",
        example = "0000-1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String accountNumber,

    @Schema(
        description = "the account balance",
        example = "0.0",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    BigDecimal balance,

    @Schema(
        description = "the account status",
        example = "ACTIVE | BLOCKED",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    Status status
) {}
