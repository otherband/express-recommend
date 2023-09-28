package org.uj.email;

public interface EmailService {
    void sendEmail(String receivedAddress, String title, String body);
}
