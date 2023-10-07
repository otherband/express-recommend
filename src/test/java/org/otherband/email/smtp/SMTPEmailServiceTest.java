package org.otherband.email.smtp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.otherband.email.VerificationLinkEmailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SMTPEmailServiceTest  {
    private static final String EXPECTED_ID = "LETTER_ID";
    public static final String EXPECTED_RECEIVER_EMAIL = "email@email.com";
    public static final String TOKEN_ID = "TOKEN_ID";
    public static final String SECRET = "SECRET";
    private SMTPEmailService service;
    private final static String SENDER_EMAIL = "test@email.com";
    private JavaMailSender mailSender;

    @BeforeEach
    void setup() {
        mailSender = mock(JavaMailSender.class);
        service = new SMTPEmailService(SENDER_EMAIL, mailSender);
    }

    @Test
    void testSendVerificationEmail() {
        VerificationLinkEmailRequest verificationLinkEmailRequest = buildRequest();
        service.sendLetterVerificationLink(verificationLinkEmailRequest);
        ArgumentCaptor<SimpleMailMessage> argumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(argumentCaptor.capture());
        SimpleMailMessage sentMessage = argumentCaptor.getValue();
        assertArrayEquals(new String[]{EXPECTED_RECEIVER_EMAIL}, sentMessage.getTo());
        assertEquals("Recommendation letter verification link", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("/api/v1/recommendation-letter/verify/LETTER_ID/TOKEN_ID/SECRET"));
    }

    private static VerificationLinkEmailRequest buildRequest() {
        VerificationLinkEmailRequest request = new VerificationLinkEmailRequest();
        request.setLetterId(EXPECTED_ID);
        request.setTokenId(TOKEN_ID);
        request.setSecretToken(SECRET);
        request.setReceiverEmail(EXPECTED_RECEIVER_EMAIL);
        return request;
    }
}