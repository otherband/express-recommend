package org.otherband.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.otherband.BaseApplicationTest;
import org.otherband.FakeEmailService;
import org.otherband.exceptions.UserInputException;
import org.otherband.letter.RecommendationLetterEntity;
import org.otherband.letter.RecommendationLetterJpaRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.otherband.TestingUtils.assertThrowsWithMessage;

public class SecretTokenServiceTest extends BaseApplicationTest {
    @Autowired
    private TokenJpaRepository tokenJpaRepository;
    @Autowired
    private RecommendationLetterJpaRepository letterJpaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FakeEmailService emailService;
    @Autowired
    private SecretTokenService tokenService;
    public static final String EMAIL = "yazan@yazan.com";
    public static final String LETTER_ID = "LETTER_ID";
    public static final String INVALID_LETTER_ID = "INVALID_LETTER_ID";

    @BeforeEach
    void setup() {
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
        TokenEntity secretToken = tokenService.create(EMAIL, LETTER_ID);
        tokenService.verify(
                emailService.lastRequest.getTokenId(),
                secretToken.getLetterId(),
                emailService.lastRequest.getSecretToken()
        );
    }

    @Test
    void verifyWithNonExistentLetter() {
        tokenService.create(EMAIL, LETTER_ID);

        assertThrowsWithMessage(UserInputException.class,
                () -> tokenService.verify(
                        emailService.lastRequest.getTokenId(),
                        INVALID_LETTER_ID,
                        emailService.lastRequest.getSecretToken()
                ),
                "No matching secrets found for letter with ID [INVALID_LETTER_ID]");
    }

    @Test
    void create() {
        TokenEntity secretToken = tokenService.create(EMAIL, LETTER_ID);
        assertAlmostNow(secretToken.getCreationDate());
        assertNotNull(secretToken.getTokenId());
        assertEquals(LETTER_ID, secretToken.getLetterId());
        assertEquals(EMAIL, secretToken.getAssociatedEmail());

        String receivedRawSecret = emailService.lastRequest.getSecretToken();

        assertTrue(
                passwordEncoder.matches(
                        receivedRawSecret,
                        secretToken.getHashedSecret()
                )
        );
    }

    @Test
    void createTokenWithNonExistentLetter() {
        assertThrowsWithMessage(UserInputException.class,
                () -> tokenService.create(EMAIL, INVALID_LETTER_ID),
                "Letter with ID [INVALID_LETTER_ID] does not exist");
    }

    private static RecommendationLetterEntity buildLetter() {
        RecommendationLetterEntity letter = new RecommendationLetterEntity();
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


    private static List<String> getInvalidEmails() {
        return List.of("", "hello@", "hello");
    }

}
