package org.uj.letter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uj.exceptions.UserInputException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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

    public static class RecommendationLetterMapRepository implements RecommendationLetterRepository {
        private final ConcurrentHashMap<String, RecommendationLetter> map = new ConcurrentHashMap<>();

        @Override
        public void save(RecommendationLetter recommendationLetter) {
            map.put(recommendationLetter.getId(), recommendationLetter);
        }

        @Override
        public void update(RecommendationLetter recommendationLetter) {
            RecommendationLetter old = map.get(recommendationLetter.getId());
            if (Objects.isNull(old)) throw new UserInputException("Cannot update letter that does not exist");
            map.put(recommendationLetter.getId(), recommendationLetter);
        }

        @Override
        public Optional<RecommendationLetter> get(String id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public List<RecommendationLetter> getAll() {
            return map.values().stream().toList();
        }
    }
}
