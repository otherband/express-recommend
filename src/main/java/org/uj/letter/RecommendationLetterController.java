package org.uj.letter;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RecommendationLetterController.LETTER_ENDPOINT)
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

    @GetMapping("/{requestId}")
    RecommendationLetter get(@PathVariable String requestId) {
        return letterRepository.get(requestId).orElseThrow(() ->
                new IllegalArgumentException(String.format("Recommendation letter with ID [%s] does not exist",
                        requestId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RecommendationLetter create(@RequestBody RecommendationLetterRequest request) {
        return letterService.create(request.author, request.body);
    }

    @Data
    static class RecommendationLetterRequest {
        private String author;
        private String body;
    }
}
