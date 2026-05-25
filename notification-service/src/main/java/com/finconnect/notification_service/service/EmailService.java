package com.finconnect.notification_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.finconnect.notification_service.dto.SendEmailRequest;
import com.finconnect.notification_service.entity.Email;
import com.finconnect.notification_service.repository.EmailRepository;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String origin;

    public String sendTextEmail(SendEmailRequest request){
        logger.info("Trying to send email");

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(origin);
            simpleMailMessage.setTo(request.destination());
            simpleMailMessage.setSubject(request.subject());
            simpleMailMessage.setText(request.message());

            Email email = new Email();
            email.setOrigin(origin);
            email.setDestination(request.destination());
            email.setSubject(request.subject());
            email.setContent(request.message());

            this.emailRepository.save(email);
            javaMailSender.send(simpleMailMessage);
            
            return "Email sended";
        } catch (Exception e) {
            logger.error("Failed to send email");
            logger.error("Error: " + e.getMessage());
            return "Failed to send email";
        }
    }
}
