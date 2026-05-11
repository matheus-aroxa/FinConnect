package com.finconnect.transaction_service.unit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import com.finconnect.transaction_service.dto.SendEmailResquest;
import com.finconnect.transaction_service.service.ReceiptProducerService;

@ExtendWith(MockitoExtension.class)
public class ReceiptProducerServiceTests {
    
    @Mock
    private KafkaTemplate<String, SendEmailResquest> kafkaTemplate;

    @InjectMocks
    private ReceiptProducerService receiptProducerService;

    @Test
    void sendMessage_ShouldCallKafkaTemplate() {
        SendEmailResquest request = new SendEmailResquest(
            "test@finconnect.com", 
            "Assunto Teste", 
            "Mensagem de teste do recibo"
        );

        receiptProducerService.sendMessage(request);

        verify(kafkaTemplate, times(1)).send(eq("receipts"), eq(request));
    }
}
