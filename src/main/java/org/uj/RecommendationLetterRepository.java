package org.uj;

import java.util.List;
import java.util.Optional;

public interface RecommendationLetterRepository {
    void save(RecommendationLetter recommendationLetter);

    void update(RecommendationLetter recommendationLetter);

    Optional<RecommendationLetter> get(String id);

    List<RecommendationLetter> getAll();
}
