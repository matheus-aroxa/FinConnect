package com.finconnect.auth_service.dto;

public record SignUpRequest(
    String fullName,
    String cpf,
    String email,
    String password
) {}
