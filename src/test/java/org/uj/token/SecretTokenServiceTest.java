package org.uj.token;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.uj.email.EmailService;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8;

public class SecretTokenServiceTest {
    public static final String EMAIL = "yazan@yazan.com";
    public static final String LETTER_ID = "LETTER_ID";
    private final Pbkdf2PasswordEncoder passwordEncoder = defaultsForSpringSecurity_v5_8();
    private final FakeEmailService emailService = new FakeEmailService();
    private final SecretTokenService tokenService = new SecretTokenService(passwordEncoder,
            emailService) {
        @Override
        protected void sendEmail(String receiverEmail, String secret) {
            emailService.receivedSecret = secret;
        }
    };

    @Test
    void create() {
        SecretToken secretToken = tokenService.create(EMAIL, LETTER_ID);
        assertAlmostNow(secretToken.getCreationDate());
        assertNotNull(secretToken.getTokenId());
        assertEquals(LETTER_ID, secretToken.getLetterId());
        assertEquals(EMAIL, secretToken.getAssociatedEmail());

        String receivedRawSecret = emailService.receivedSecret;

        assertTrue(
                passwordEncoder.matches(
                        receivedRawSecret,
                        secretToken.getHashedSecret()
                )
        );


    }

    private void assertAlmostNow(LocalDateTime creationDate) {
        assertThat(Duration.between(creationDate, LocalDateTime.now()).toMillis())
                .isLessThan(1000);
    }

    @Getter
    static class FakeEmailService implements EmailService {

        private String receivedSecret;

        @Override
        public void sendEmail(String receivedAddress, String title, String body) {
        }
    }


}
