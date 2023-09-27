package org.uj.e2e;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.uj.letter.RecommendationLetter;
import org.uj.letter.RecommendationLetterRepository;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.uj.letter.RecommendationLetterController.LETTER_ENDPOINT;
import static org.uj.letter.RecommendationLetterController.RecommendationLetterRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class EndToEndTest {

    private static final Gson GSON = new Gson();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private RecommendationLetterRepository letterRepository;

    @Test
    void getAll() throws Exception {
        this.mockMvc.perform(get(LETTER_ENDPOINT)).andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        RecommendationLetter recommendationLetter = new RecommendationLetter();
        recommendationLetter.setId("VALID_ID");
        letterRepository.save(recommendationLetter);
        mockMvc.perform(get(byId("INVALID_ID"))).andExpect(status().isBadRequest());
        mockMvc.perform(get(byId("VALID_ID"))).andExpect(status().isOk());
    }

    @Test
    void post() throws Exception {
        RecommendationLetterRequest request = buildRequest();
        request.setBody(UUID.randomUUID().toString());
        mockMvc.perform(MockMvcRequestBuilders.post(LETTER_ENDPOINT)
                        .content(GSON.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    RecommendationLetter letter = parse(result);
                    assertThat(letterRepository.get(letter.getId())).isPresent();
                });


    }

    private static RecommendationLetter parse(MvcResult result) throws UnsupportedEncodingException {
        return GSON.fromJson(result.getResponse().getContentAsString(), RecommendationLetter.class);
    }

    private static String byId(String id) {
        return LETTER_ENDPOINT.concat("/").concat(id);
    }

    private static RecommendationLetterRequest buildRequest() {
        RecommendationLetterRequest request = new RecommendationLetterRequest();
        request.setAuthor("AUTHOR");
        request.setBody("BODY");
        return request;
    }
}
