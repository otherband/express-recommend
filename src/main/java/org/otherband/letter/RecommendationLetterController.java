package org.otherband.letter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.otherband.exceptions.UserInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    private static UserInputException letterDoesNotExist(String letterId) {
        return new UserInputException(String.format("Recommendation letter with ID [%s] does not exist",
                letterId));
    }

    @GetMapping
    List<RecommendationLetterEntity> getAll() {
        return letterRepository.getAll();
    }

    @GetMapping("/verify/{letterId}/{tokenId}/{secret}")
    public RecommendationLetterEntity verify(@PathVariable String letterId, @PathVariable String tokenId, @PathVariable String secret) {
        return letterService.verify(tokenId, letterId, secret);
    }

    @GetMapping("/{letterId}")
    RecommendationLetterEntity get(@PathVariable String letterId) {
        return letterRepository.get(letterId)
                .orElseThrow(() -> letterDoesNotExist(letterId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RecommendationLetterEntity create(@RequestBody @Valid RecommendationLetterRequest request) {
        return letterService.create(request.authorEmail, request.body);
    }

    @Data
    public static class RecommendationLetterRequest {
        @Email
        private String authorEmail;
        @NotBlank
        private String body;
    }
}
