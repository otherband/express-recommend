package org.uj.letter;

import org.springframework.stereotype.Repository;
import org.uj.exceptions.UserInputException;

import java.util.List;
import java.util.Optional;

@Repository
public class RecommendationLetterRepositoryImpl implements RecommendationLetterRepository {

    private final RecommendationLetterJpaRepository recommendationLetterJpaRepository;

    public RecommendationLetterRepositoryImpl(RecommendationLetterJpaRepository recommendationLetterJpaRepository) {
        this.recommendationLetterJpaRepository = recommendationLetterJpaRepository;
    }

    @Override
    public void save(RecommendationLetter recommendationLetter) {
        recommendationLetterJpaRepository.save(recommendationLetter);
    }

    @Override
    public void update(RecommendationLetter updatedLetter) {
        recommendationLetterJpaRepository
                .findById(updatedLetter.getId())
                .map(oldLetter -> recommendationLetterJpaRepository.save(updatedLetter))
                .orElseThrow(() -> letterToUpdateDoesNotExist(updatedLetter)
                );
    }

    @Override
    public Optional<RecommendationLetter> get(String id) {
        return recommendationLetterJpaRepository.findById(id);
    }

    @Override
    public List<RecommendationLetter> getAll() {
        return recommendationLetterJpaRepository.findAll();
    }

    private static UserInputException letterToUpdateDoesNotExist(RecommendationLetter updatedLetter) {
        return new UserInputException(String.format("Cannot update letter with ID [%s] because it does not exist", updatedLetter.getId()));
    }
}
