package com.finconnect.notification_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.finconnect.notification_service.dto.SendEmailRequest;

@Service
public class ReceiptConsumerService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReceiptConsumerService.class);

    @Autowired
    private EmailService emailService;
    
    @KafkaListener(topics = "receipts", groupId = "receipt-group")
    public void consume(SendEmailRequest request) {
        logger.info("Email request received");

        emailService.sendTextEmail(request);
    }
}
