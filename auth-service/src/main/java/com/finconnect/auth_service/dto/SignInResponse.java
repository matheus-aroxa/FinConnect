package com.finconnect.auth_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The signin response")
public record SignInResponse(

    @Schema(
        description = "Valid jwt to access blocked endpoints",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkphbmUgRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTc3OTM1NDAwMCwiZXhwIjoxNzc5MzU3NjAwfQ.0cK3u4o8iXf4g6w8d1B2s4N6v9W1d3F5g2K4l8L1m3k",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String jwt,

    @Schema(
        description = "Refresh token",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkphbmUgRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTc3OTM1NDAwMCwiZXhwIjoxNzc5MzU3NjAwfQ.0cK3u4o8iXf4g6w8d1B2s4N6v9W1d3F5g2K4l8L1m3k",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String refreshToken
) {}