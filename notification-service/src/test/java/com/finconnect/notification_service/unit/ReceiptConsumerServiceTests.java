package com.finconnect.notification_service.unit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.finconnect.notification_service.dto.SendEmailRequest;
import com.finconnect.notification_service.service.EmailService;
import com.finconnect.notification_service.service.ReceiptConsumerService;

@ExtendWith(MockitoExtension.class)
public class ReceiptConsumerServiceTests {
    
    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReceiptConsumerService receiptConsumerService;

    @Test
    void callEmailService() {
        SendEmailRequest request = new SendEmailRequest("user@finconnect.com", "Recibo", "Seu recibo chegou");

        receiptConsumerService.consume(request);

        verify(emailService, times(1)).sendTextEmail(request);
    }
}
