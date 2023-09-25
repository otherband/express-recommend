package org.uj;

import java.util.List;

public interface RecommendationLetterRepository {
    void save(RecommendationLetter recommendationLetter);

    void update(RecommendationLetter recommendationLetter);

    RecommendationLetter get(String id);

    List<RecommendationLetter> getAll();
}
