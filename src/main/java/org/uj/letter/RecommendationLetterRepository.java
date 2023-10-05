package org.uj.letter;

import java.util.List;
import java.util.Optional;

public interface RecommendationLetterRepository {
    void save(RecommendationLetterEntity recommendationLetter);

    void update(RecommendationLetterEntity recommendationLetter);

    Optional<RecommendationLetterEntity> get(String id);

    List<RecommendationLetterEntity> getAll();
}
