package org.uj.letter;

import org.springframework.stereotype.Service;
import org.uj.token.SecretTokenService;

import java.util.UUID;

import static org.uj.Utils.validateNotBlank;

@Service
public class RecommendationLetterService {
    private final RecommendationLetterRepository repository;
    private final SecretTokenService tokenService;

    public RecommendationLetterService(RecommendationLetterRepository repository, SecretTokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    public RecommendationLetterEntity create(String authorEmail, String body) {

        validateNotBlank(authorEmail, "Author");
        validateNotBlank(body, "Body");

        RecommendationLetterEntity recommendationLetter = new RecommendationLetterEntity();
        recommendationLetter.setAuthorEmail(authorEmail);
        recommendationLetter.setBody(body);
        recommendationLetter.setId(UUID.randomUUID().toString());
        recommendationLetter.setValidated(false);

        repository.save(recommendationLetter);
        tokenService.create(authorEmail, recommendationLetter.getId());
        return recommendationLetter;
    }

}
