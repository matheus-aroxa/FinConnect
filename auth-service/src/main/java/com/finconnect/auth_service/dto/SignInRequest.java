package com.finconnect.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to sign in")
public record SignInRequest(

    @Schema(
        description = "user's username",
        example = "test@teste.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String username,

    @Schema(
        description = "user's password",
        example = "password123",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String password
) {}
