package com.finconnect.auth_service.dto;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "The data extracted from jwt")
public record UserInfoFromJwtResponse(

    @Schema(
        description = "user's username",
        example = "Pedro Pascal Motta",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String username,

    @Schema(
        description = "the jwt expiry date",
        example = "dow mon dd hh:mm:ss zzz yyyy",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    Date expirationDate,

    @Schema(
        description = "the jwt issue date",
        example = "dow mon dd hh:mm:ss zzz yyyy",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    Date issueDate
) {}
