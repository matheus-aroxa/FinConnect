package com.finconnect.auth_service.dto;

public record SignInResponse(
    String jwt,
    String refreshToken
) {}