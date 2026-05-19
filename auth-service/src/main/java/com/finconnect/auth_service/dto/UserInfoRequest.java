package com.finconnect.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to extract data from jwt token")
public record UserInfoRequest (

    @Schema(
        description = "the jwt token",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String token
) {}