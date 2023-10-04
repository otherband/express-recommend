package org.uj.email;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
public class EmailServiceSimulator implements EmailService {
    @Override
    public void sendEmail(String receivedAddress, String title, String body) {

    }
}
