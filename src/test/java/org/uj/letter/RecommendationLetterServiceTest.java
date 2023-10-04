package org.uj.letter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.uj.BaseJpaTest;
import org.uj.exceptions.UserInputException;
import org.uj.token.SecretTokenService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RecommendationLetterServiceTest extends BaseJpaTest {
    public static final String AUTHOR = "AUTHOR";
    public static final String BODY = "BODY";
    private RecommendationLetterService service;
    @Autowired
    private RecommendationLetterJpaRepository recommendationLetterJpaRepository;
    private SecretTokenService tokenService;

    @BeforeEach
    void setup() {
        tokenService = mock(SecretTokenService.class);
        service = new RecommendationLetterService(
                new RecommendationLetterRepositoryImpl(recommendationLetterJpaRepository),
                tokenService);
    }

    @Test
    void createInvalid() {
        assertThrows(UserInputException.class, () -> service.create("", BODY));
        assertThrows(UserInputException.class, () -> service.create(AUTHOR, ""));
    }

    @Test
    void create() {
        RecommendationLetter letter = service.create(AUTHOR, BODY);
        assertNotNull(letter.getId());
        assertEquals(AUTHOR, letter.getAuthorEmail());
        assertEquals(BODY, letter.getBody());
        assertFalse(letter.isValidated());
        verify(tokenService).create(AUTHOR, letter.getId());
    }

}
