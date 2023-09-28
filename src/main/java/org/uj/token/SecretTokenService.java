package org.uj.token;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.uj.email.EmailService;

import java.time.LocalDateTime;
import java.util.UUID;

public class SecretTokenService {

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public SecretTokenService(PasswordEncoder passwordEncoder, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public SecretToken create(String receiverEmail, String letterId) {
        SecretToken secretToken = new SecretToken();
        secretToken.setCreationDate(LocalDateTime.now());
        secretToken.setLetterId(letterId);
        secretToken.setTokenId(randomString());
        secretToken.setAssociatedEmail(receiverEmail);
        handleSecret(receiverEmail, secretToken);
        return secretToken;
    }

    private void handleSecret(String receiverEmail, SecretToken secretToken) {
        String secret = randomString().concat(randomString());
        sendEmail(receiverEmail, secret);
        secretToken.setHashedSecret(passwordEncoder.encode(secret));
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }

    protected void sendEmail(String receiverEmail, String secret) {
        emailService.sendEmail(receiverEmail,
                "Verification link for recommendation letter",
                secret);
    }
}
