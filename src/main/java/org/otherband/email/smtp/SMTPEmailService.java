package org.otherband.email.smtp;

import org.otherband.Utils;
import org.otherband.email.EmailService;
import org.otherband.email.VerificationLinkEmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Profile("prod")
@Service
public class SMTPEmailService implements EmailService {
    private final String mailUserName;
    private final JavaMailSender mailSender;

    public SMTPEmailService(@Value("${spring.mail.username}") String mailUserName, JavaMailSender mailSender) {
        this.mailUserName = mailUserName;
        this.mailSender = mailSender;
    }

    @Override
    public void sendLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailUserName);
        message.setTo(verificationLinkEmailRequest.getReceiverEmail());
        message.setText(Utils.buildLetterVerificationLink(verificationLinkEmailRequest));
        message.setSubject("Recommendation letter verification link");
        mailSender.send(message);
    }
}