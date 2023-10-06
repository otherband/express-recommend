package org.otherband.email.smtp;

import org.otherband.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SMTPEmailService {
    @Value("${spring.mail.username}")
    private String mailUserName;
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailDetails smtpEmailDetails) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailUserName);
        message.setTo(smtpEmailDetails.getRecipient());
        message.setText(smtpEmailDetails.getEmailBody());
        message.setSubject(smtpEmailDetails.getSubject());
        mailSender.send(message);
    }
}