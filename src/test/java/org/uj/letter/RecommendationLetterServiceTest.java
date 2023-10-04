package org.uj.letter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.uj.BaseJpaTest;
import org.uj.exceptions.UserInputException;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendationLetterServiceTest extends BaseJpaTest {
    public static final String AUTHOR = "AUTHOR";
    public static final String BODY = "BODY";
    private RecommendationLetterService service;
    @Autowired
    private RecommendationLetterJpaRepository recommendationLetterJpaRepository;

    @BeforeEach
    void setup() {
        service = new RecommendationLetterService(new RecommendationLetterRepositoryImpl(recommendationLetterJpaRepository));
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
    }

}
