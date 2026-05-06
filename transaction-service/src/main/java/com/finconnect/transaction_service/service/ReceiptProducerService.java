package com.finconnect.transaction_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.finconnect.transaction_service.dto.SendEmailResquest;

@Service
public class ReceiptProducerService {

    @Autowired
    private KafkaTemplate<String, SendEmailResquest> kafkaTemplate;

    public void sendMessage(SendEmailResquest request) {
        kafkaTemplate.send("receipts", request);
    }
}
