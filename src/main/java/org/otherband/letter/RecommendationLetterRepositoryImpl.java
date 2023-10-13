package org.otherband.letter;

import org.otherband.exceptions.UserInputException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecommendationLetterRepositoryImpl implements RecommendationLetterRepository {

    private final RecommendationLetterJpaRepository recommendationLetterJpaRepository;

    public RecommendationLetterRepositoryImpl(RecommendationLetterJpaRepository recommendationLetterJpaRepository) {
        this.recommendationLetterJpaRepository = recommendationLetterJpaRepository;
    }

    private static UserInputException letterToUpdateDoesNotExist(RecommendationLetterEntity updatedLetter) {
        return new UserInputException(String.format("Cannot update letter with ID [%s] because it does not exist",
                updatedLetter.getId()));
    }

    @Override
    public RecommendationLetterEntity save(RecommendationLetterEntity recommendationLetter) {
        return recommendationLetterJpaRepository.save(recommendationLetter);
    }

    @Override
    public RecommendationLetterEntity update(RecommendationLetterEntity updatedLetter) {
        return recommendationLetterJpaRepository
                .findById(updatedLetter.getId())
                .map(oldLetter -> recommendationLetterJpaRepository.save(updatedLetter))
                .orElseThrow(() -> letterToUpdateDoesNotExist(updatedLetter));
    }

    @Override
    public Optional<RecommendationLetterEntity> get(String id) {
        return recommendationLetterJpaRepository.findById(id);
    }

    @Override
    public List<RecommendationLetterEntity> getAll() {
        return recommendationLetterJpaRepository.findAll();
    }
}
