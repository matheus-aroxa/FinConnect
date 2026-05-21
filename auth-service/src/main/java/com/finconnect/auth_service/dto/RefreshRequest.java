package com.finconnect.auth_service.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(

    @NotBlank
    String refreshToken
) {}