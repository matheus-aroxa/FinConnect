package com.finconnect.transaction_service.dto;

public record SendEmailResquest(
    String destination,
    String subject,
    String message
) {}
