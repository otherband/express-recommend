package org.otherband;

import org.otherband.email.EmailService;
import org.otherband.email.VerificationLinkEmailRequest;

public class FakeEmailService implements EmailService {

    public VerificationLinkEmailRequest lastRequest;
    @Override
    public void sendLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest) {
        lastRequest = verificationLinkEmailRequest;
    }
}
