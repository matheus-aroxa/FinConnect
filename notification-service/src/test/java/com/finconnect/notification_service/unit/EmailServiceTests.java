package com.finconnect.notification_service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import com.finconnect.notification_service.dto.SendEmailRequest;
import com.finconnect.notification_service.service.EmailService;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTests {
    
    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    private SendEmailRequest request;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "origin", "noreply@finconnect.com");

        request = new SendEmailRequest("test@example.com", "subject", "content");
    }

    @Test
    void sendEmailWithSuccess() {
        String result = emailService.sendTextEmail(request);

        assertEquals("Email sended", result);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assertEquals(request.destination(), capturedMessage.getTo()[0]);
        assertEquals(request.subject(), capturedMessage.getSubject());
        assertEquals(request.message(), capturedMessage.getText());
    }

    @Test
    void sendEmailError() {
        doThrow(new RuntimeException("Erro de conexão")).when(javaMailSender).send(any(SimpleMailMessage.class));

        String result = emailService.sendTextEmail(request);

        assertEquals("Failed to send email", result);
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
