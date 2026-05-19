package com.finconnect.auth_service.dto;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sign Up request")
public record SignUpRequest(

    @Schema(
        description = "user's fullname",
        example = "Pedro Pascal Motta",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String fullName,

    @Schema(
        description = "user's CPF without symbols",
        example = "XXXXXXXXXXX",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @CPF
    String cpf,

    @Schema(
        description = "user's email",
        example = "test@test.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String email,

    @Schema(
        description = "user's password",
        example = "password123",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String password
) {}
