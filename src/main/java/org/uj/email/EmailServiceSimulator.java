package org.uj.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class EmailServiceSimulator implements EmailService {
    @Override
    public void sendEmail(String receivedAddress, String title, String body) {


    }
}
