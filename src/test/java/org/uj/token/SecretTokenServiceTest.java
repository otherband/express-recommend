package org.uj.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.uj.BaseJpaTest;
import org.uj.email.EmailService;
import org.uj.email.VerificationLinkEmailRequest;
import org.uj.exceptions.UserInputException;
import org.uj.letter.RecommendationLetter;
import org.uj.letter.RecommendationLetterJpaRepository;
import org.uj.letter.RecommendationLetterRepository;
import org.uj.letter.RecommendationLetterRepositoryImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8;

@DataJpaTest
public class SecretTokenServiceTest extends BaseJpaTest {
    @Autowired
    private TokenJpaRepository tokenJpaRepository;
    @Autowired
    private RecommendationLetterJpaRepository letterJpaRepository;
    public static final String EMAIL = "yazan@yazan.com";
    public static final String LETTER_ID = "LETTER_ID";
    private final Pbkdf2PasswordEncoder passwordEncoder = defaultsForSpringSecurity_v5_8();
    private final FakeEmailService emailService = new FakeEmailService();
    private SecretTokenService tokenService;

    @BeforeEach
    void setup() {
        tokenService = new TestableTokenService(
                emailService,
                new TokenRepositoryImpl(tokenJpaRepository),
                new RecommendationLetterRepositoryImpl(letterJpaRepository)
        );
        letterJpaRepository.save(buildLetter());
    }

    @ParameterizedTest
    @Disabled
    @MethodSource("getInvalidEmails")
    void createTokenWithInvalidEmail(String invalidEmail) {
        assertThrows(UserInputException.class, () -> tokenService.create(invalidEmail, LETTER_ID));
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

    private static RecommendationLetter buildLetter() {
        RecommendationLetter letter = new RecommendationLetter();
        letter.setId(LETTER_ID);
        letter.setBody("Letter body");
        letter.setAuthorEmail("Letter Author");
        letter.setValidated(false);
        return letter;
    }

    private void assertAlmostNow(LocalDateTime creationDate) {
        assertThat(Duration.between(creationDate, LocalDateTime.now()).toMillis())
                .isLessThan(5000);
    }

    public static class FakeEmailService implements EmailService {

        private String receivedSecret;
        private String receivedId;

        @Override
        public void sendLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest) {
        }
    }

    static class TestableTokenService extends SecretTokenService {
        private final FakeEmailService fakeEmailService;

        public TestableTokenService(FakeEmailService emailService, TokenRepository tokenRepository, RecommendationLetterRepository letterRepository) {
            super(emailService, tokenRepository, letterRepository, defaultsForSpringSecurity_v5_8());
            fakeEmailService = emailService;
        }

        @Override
        protected void sendEmail(String receiverEmail, String secret, String tokenId, String letterId) {
            fakeEmailService.receivedSecret = secret;
            fakeEmailService.receivedId = tokenId;
        }
    }

    private static List<String> getInvalidEmails() {
        return List.of("", "hello@", "hello");
    }

}
