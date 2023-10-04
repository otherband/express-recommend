package org.uj.email;

import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@Component
public class EmailServiceSimulator implements EmailService {
    @Override
    public void sendEmail(String receivedAddress, String title, String body) {


    }
}
