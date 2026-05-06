package com.finconnect.notification_service.dto;

public record SendEmailResquest(
    String destination,
    String subject,
    String message
) {}
