package com.finconnect.notification_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Document(collection = "email")
public class Email {
    
    @Id
    private String id;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;
}
