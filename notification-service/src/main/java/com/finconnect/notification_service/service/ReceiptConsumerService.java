package com.finconnect.notification_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.finconnect.notification_service.dto.ReceiptRequest;

@Service
public class ReceiptConsumerService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReceiptConsumerService.class);
    
    @KafkaListener(topics = "receipts", groupId = "receipt-group")
    public void consume(ReceiptRequest receipt) {
        logger.info("Mensagem recebida com sucesso!");
    }
}
