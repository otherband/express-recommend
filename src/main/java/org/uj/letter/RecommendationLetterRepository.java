package org.uj.letter;

import java.util.List;
import java.util.Optional;

public interface RecommendationLetterRepository {
    RecommendationLetterEntity save(RecommendationLetterEntity recommendationLetter);

    RecommendationLetterEntity update(RecommendationLetterEntity recommendationLetter);

    Optional<RecommendationLetterEntity> get(String id);

    List<RecommendationLetterEntity> getAll();
}
