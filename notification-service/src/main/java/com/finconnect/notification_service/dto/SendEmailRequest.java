package com.finconnect.notification_service.dto;

public record SendEmailRequest(
    String destination,
    String subject,
    String message
) {}
