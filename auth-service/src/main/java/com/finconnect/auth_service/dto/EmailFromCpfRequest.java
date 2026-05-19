package com.finconnect.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to get the user's email from cpf")
public record EmailFromCpfRequest(

    @Schema(
        description = "user's cpf without symbols",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String cpf
) {}
