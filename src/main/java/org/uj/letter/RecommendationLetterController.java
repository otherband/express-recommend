package org.uj.letter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uj.exceptions.UserInputException;

import java.util.List;

@RestController
@RequestMapping(RecommendationLetterController.LETTER_ENDPOINT)
@CrossOrigin("*")
public class RecommendationLetterController {

    public static final String LETTER_ENDPOINT = "/api/v1/recommendation-letter";
    private final RecommendationLetterService letterService;
    private final RecommendationLetterRepository letterRepository;

    public RecommendationLetterController(RecommendationLetterService letterService, RecommendationLetterRepository letterRepository) {
        this.letterService = letterService;
        this.letterRepository = letterRepository;
    }

    @GetMapping
    List<RecommendationLetter> getAll() {
        return letterRepository.getAll();
    }

    @GetMapping("/{letterId}")
    RecommendationLetter get(@PathVariable String letterId) {
        return letterRepository.get(letterId)
                .orElseThrow(() -> letterDoesNotExist(letterId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RecommendationLetter create(@RequestBody @Valid RecommendationLetterRequest request) {
        return letterService.create(request.authorEmail, request.body);
    }

    @Data
    public static class RecommendationLetterRequest {
        @Email
        private String authorEmail;
        @NotBlank
        private String body;
    }

    private static UserInputException letterDoesNotExist(String letterId) {
        return new UserInputException(String.format("Recommendation letter with ID [%s] does not exist",
                letterId));
    }
}
