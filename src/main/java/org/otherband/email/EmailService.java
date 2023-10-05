package org.otherband.email;

public interface EmailService {
    void sendLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest);
}
