package com.finconnect.account_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create account")
public record CreateAccount(

    @Schema(
        description = "User's cpf without symbols",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String cpf
) {}
