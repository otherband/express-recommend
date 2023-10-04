package org.uj.email;

public interface EmailService {
    void sendLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest);
}
