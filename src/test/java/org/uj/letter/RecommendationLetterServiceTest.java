package org.uj.letter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendationLetterServiceTest {
    public static final String AUTHOR = "AUTHOR";
    public static final String BODY = "BODY";
    private RecommendationLetterService service;

    @BeforeEach
    void setup() {
        service = new RecommendationLetterService(new RecommendationLetterMapRepository());
    }

    @Test
    void create() {
        RecommendationLetter letter = service.create(AUTHOR, BODY);
        assertNotNull(letter.getId());
        assertEquals(AUTHOR, letter.getAuthor());
        assertEquals(BODY, letter.getBody());
        assertFalse(letter.isValidated());
    }
}
