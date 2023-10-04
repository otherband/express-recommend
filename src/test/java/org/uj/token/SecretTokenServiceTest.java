package org.uj.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.uj.email.EmailService;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8;

@DataJpaTest
public class SecretTokenServiceTest {
    @Autowired
    private TokenJpaRepository tokenJpaRepository;
    public static final String EMAIL = "yazan@yazan.com";
    public static final String LETTER_ID = "LETTER_ID";
    private final Pbkdf2PasswordEncoder passwordEncoder = defaultsForSpringSecurity_v5_8();
    private final FakeEmailService emailService = new FakeEmailService();
    private SecretTokenService tokenService;

    @BeforeEach
    void setup() {
        tokenService = new TestableTokenService(
                passwordEncoder,
                emailService,
                new TokenRepositoryImpl(tokenJpaRepository)
        );
    }


    @Test
    void verify() {
        SecretToken secretToken = tokenService.create(EMAIL, LETTER_ID);
        tokenService.verify(
                emailService.receivedId,
                secretToken.getLetterId(),
                emailService.receivedSecret
        );
    }

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

    static class FakeEmailService implements EmailService {

        private String receivedSecret;
        private String receivedId;

        @Override
        public void sendEmail(String receivedAddress, String title, String body) {
        }
    }

    static class TestableTokenService extends SecretTokenService {
        private final FakeEmailService fakeEmailService;

        public TestableTokenService(PasswordEncoder passwordEncoder, FakeEmailService emailService, TokenRepository tokenRepository) {
            super(passwordEncoder, emailService, tokenRepository);
            fakeEmailService = emailService;
        }

        @Override
        protected void sendEmail(String receiverEmail, String secret, String tokenId) {
            fakeEmailService.receivedSecret = secret;
            fakeEmailService.receivedId = tokenId;
        }
    }


}
