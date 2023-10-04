package org.uj.letter;

import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.uj.Utils.validateNotBlank;

@Service
public class RecommendationLetterService {
    private final RecommendationLetterRepository repository;

    public RecommendationLetterService(RecommendationLetterRepository repository) {
        this.repository = repository;
    }

    public RecommendationLetter create(String authorEmail, String body) {

        validateNotBlank(authorEmail, "Author");
        validateNotBlank(body, "Body");

        RecommendationLetter recommendationLetter = new RecommendationLetter();
        recommendationLetter.setAuthorEmail(authorEmail);
        recommendationLetter.setBody(body);
        recommendationLetter.setId(UUID.randomUUID().toString());
        recommendationLetter.setValidated(false);

        repository.save(recommendationLetter);
        return recommendationLetter;
    }

}
