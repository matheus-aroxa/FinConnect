package com.finconnect.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The email extracted from user's cpf")
public record EmailFromCpfResponse(

    @Schema(
        description = "user's email",
        example = "test@test.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String email
) {}
