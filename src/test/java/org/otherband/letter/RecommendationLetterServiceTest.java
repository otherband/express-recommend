package org.otherband.letter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.otherband.BaseApplicationTest;
import org.otherband.FakeEmailService;
import org.otherband.exceptions.UserInputException;
import org.otherband.token.TokenRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.otherband.TestingUtils.assertThrowsWithMessage;

public class RecommendationLetterServiceTest extends BaseApplicationTest {
    @Autowired
    private RecommendationLetterRepository letterRepository;
    @Autowired
    private FakeEmailService emailService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RecommendationLetterService service;
    public static final String AUTHOR_EMAIL = "author@email.com";
    public static final String BODY = "BODY";


    @Test
    void createInvalid() {
        assertThrows(UserInputException.class, () -> service.create("", BODY));
        assertThrows(UserInputException.class, () -> service.create(AUTHOR_EMAIL, ""));
    }

    @Test
    void create() {
        RecommendationLetterEntity letter = service.create(AUTHOR_EMAIL, BODY);
        assertNotNull(letter.getId());
        assertEquals(AUTHOR_EMAIL, letter.getAuthorEmail());
        assertEquals(BODY, letter.getBody());
        assertFalse(letter.isValidated());
        assertThat(tokenRepository.getByLetterId(letter.getId())).asList().hasSize(1);
    }

    @Test
    void verifyValidLetter() {
        RecommendationLetterEntity letter = service.create(AUTHOR_EMAIL, BODY);

        assertDoesNotThrow(() ->
                service.verify(emailService.lastRequest.getTokenId(),
                        emailService.lastRequest.getLetterId(),
                        emailService.lastRequest.getSecretToken())
        );

        assertThat(letterRepository.get(letter.getId())).hasValueSatisfying(
                recommendationLetterEntity -> assertTrue(recommendationLetterEntity.isValidated()));
    }

    @Test
    void invalidVerification() {
        RecommendationLetterEntity letter = service.create(AUTHOR_EMAIL, BODY);

        assertThrowsWithMessage(UserInputException.class, () ->
                        service.verify(emailService.lastRequest.getTokenId(),
                                emailService.lastRequest.getLetterId(),
                                "INVALID_SECRET"),
                String.format("No matching secrets found for letter with ID [%s]", letter.getId())
        );

        assertThat(letterRepository.get(letter.getId())).hasValueSatisfying(
                recommendationLetterEntity -> assertFalse(recommendationLetterEntity.isValidated()));
    }

}
