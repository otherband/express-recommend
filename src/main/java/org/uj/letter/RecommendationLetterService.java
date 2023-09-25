package org.uj.letter;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecommendationLetterService {
    private final RecommendationLetterRepository repository;

    public RecommendationLetterService(RecommendationLetterRepository repository) {
        this.repository = repository;
    }

    public RecommendationLetter create(String author, String body) {
        RecommendationLetter recommendationLetter = new RecommendationLetter();
        recommendationLetter.setAuthor(author);
        recommendationLetter.setBody(body);
        recommendationLetter.setId(UUID.randomUUID().toString());
        recommendationLetter.setValidated(false);

        repository.save(recommendationLetter);
        return recommendationLetter;
    }
}
