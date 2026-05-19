package com.finconnect.notification_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "Request to send an email")
public record SendEmailRequest(

    @Schema(
        description = "email destination",
        example = "destination@test.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Email
    String destination,

    @Schema(
        description = "Email's subject",
        example = "Subject",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String subject,

    @Schema(
        description = "Email's message",
        example = "Message",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    String message
) {}
