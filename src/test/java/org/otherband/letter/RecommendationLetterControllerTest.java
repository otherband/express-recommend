package org.otherband.letter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.otherband.BaseApplicationTest;
import org.otherband.FakeEmailService;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.otherband.letter.RecommendationLetterController.LETTER_ENDPOINT;
import static org.otherband.letter.RecommendationLetterController.RecommendationLetterRequest;

@SpringBootTest
public class RecommendationLetterControllerTest extends BaseApplicationTest {

    public static final String VALID_ID = "VALID_ID";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private RecommendationLetterRepository letterRepository;
    @Autowired
    private FakeEmailService emailService;

    @Test
    void getAll() throws Exception {
        this.mockMvc.perform(get(LETTER_ENDPOINT)).andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        RecommendationLetterEntity recommendationLetter = create();
        letterRepository.save(recommendationLetter);
        mockMvc.perform(get(byId("INVALID_ID"))).andExpect(status().isBadRequest());
        mockMvc.perform(get(byId(VALID_ID))).andExpect(status().isOk());
    }

    @Test
    void createAndVerify() throws Exception {
        RecommendationLetterRequest request = buildRequest();
        request.setBody(UUID.randomUUID().toString());
        mockMvc.perform(post(LETTER_ENDPOINT)
                        .content(GSON.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    RecommendationLetterEntity letter = parse(result);
                    assertThat(letterRepository.get(letter.getId())).isPresent();
                });

        mockMvc.perform(get(LETTER_ENDPOINT.concat("/verify/{letterId}/{tokenId}/{secret}"),
                        emailService.lastRequest.getLetterId(),
                        emailService.lastRequest.getTokenId(),
                        emailService.lastRequest.getSecretToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertThat(letterRepository.get(emailService.lastRequest.getLetterId())).hasValueSatisfying(
                                verifiedLetter -> assertTrue(verifiedLetter.isValidated()
                                )
                        ));
    }

    private static RecommendationLetterEntity create() {
        RecommendationLetterEntity recommendationLetter = new RecommendationLetterEntity();
        recommendationLetter.setId(VALID_ID);
        recommendationLetter.setBody("BODY");
        recommendationLetter.setAuthorEmail("AUTHOR");
        return recommendationLetter;
    }

    private static RecommendationLetterEntity parse(MvcResult result) throws UnsupportedEncodingException {
        return GSON.fromJson(result.getResponse().getContentAsString(), RecommendationLetterEntity.class);
    }

    private static String byId(String id) {
        return LETTER_ENDPOINT.concat("/").concat(id);
    }

    private static RecommendationLetterRequest buildRequest() {
        RecommendationLetterRequest request = new RecommendationLetterRequest();
        request.setAuthorEmail("author@email.com");
        request.setBody("LETTER BODY");
        return request;
    }
}
