package org.uj;

import org.uj.email.EmailService;
import org.uj.email.VerificationLinkEmailRequest;

public class FakeEmailService implements EmailService {

    public VerificationLinkEmailRequest lastRequest;
    @Override
    public void sendLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest) {
        lastRequest = verificationLinkEmailRequest;
    }
}
