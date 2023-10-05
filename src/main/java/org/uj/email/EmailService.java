package org.uj.email;

public interface EmailService {
    void sendLetterVerificationLink(VerificationLinkEmailRequestDto verificationLinkEmailRequest);
}
