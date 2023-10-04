package org.uj.letter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RecommendationLetterServiceTest {
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
    void create() {
        RecommendationLetter letter = service.create(AUTHOR, BODY);
        assertNotNull(letter.getId());
        assertEquals(AUTHOR, letter.getAuthor());
        assertEquals(BODY, letter.getBody());
        assertFalse(letter.isValidated());
    }

}
