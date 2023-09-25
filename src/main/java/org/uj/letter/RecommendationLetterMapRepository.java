package org.uj.letter;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RecommendationLetterMapRepository implements RecommendationLetterRepository {
    private final ConcurrentHashMap<String, RecommendationLetter> map = new ConcurrentHashMap<>();
    @Override
    public void save(RecommendationLetter recommendationLetter) {
        map.put(recommendationLetter.getId(), recommendationLetter);
    }

    @Override
    public void update(RecommendationLetter recommendationLetter) {
        RecommendationLetter old = map.get(recommendationLetter.getId());
        if (Objects.isNull(old)) throw new IllegalArgumentException("Cannot update letter that does not exist");
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
