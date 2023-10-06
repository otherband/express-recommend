package org.otherband.email.smtp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.otherband.email.VerificationLinkEmailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class SMTPEmailServiceTest  {
    private SMTPEmailService service;
    private String mailUserName;
    private JavaMailSender mailSender;

    @BeforeEach
    void setup() {
        mailUserName = "test";
        mailSender = mock(JavaMailSender.class);
        service = new SMTPEmailService(mailUserName, mailSender);
    }

    @Test
    void testSendVerificationEmail() {
        VerificationLinkEmailRequest verificationLinkEmailRequest = new VerificationLinkEmailRequest();
        service.sendLetterVerificationLink(verificationLinkEmailRequest);
        verify(mailSender).send(Mockito.any(SimpleMailMessage.class));
    }
}