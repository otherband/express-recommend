package org.uj.letter;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.uj.letter.RecommendationLetterController.LETTER_ENDPOINT;
import static org.uj.letter.RecommendationLetterController.RecommendationLetterRequest;

@WebMvcTest(RecommendationLetterController.class)
public class RecommendationLetterControllerTest {

    public static final Gson GSON = new Gson();
    public static final String AUTHOR = "AUTHOR";
    public static final String BODY = "BODY";
    public static final String INVALID_ID = "INVALID_ID";
    public static final String VALID_ID = "VALID_ID";
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RecommendationLetterService recommendationLetterService;
    @MockBean
    private RecommendationLetterRepository letterRepository;

    @Test
    void getAll() throws Exception {
        this.mockMvc.perform(get(LETTER_ENDPOINT)).andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        when(letterRepository.get(INVALID_ID)).thenReturn(Optional.empty());
        when(letterRepository.get(VALID_ID)).thenReturn(Optional.of(new RecommendationLetter()));
        mockMvc.perform(get(byId(INVALID_ID))).andExpect(status().isBadRequest());
        mockMvc.perform(get(byId(VALID_ID))).andExpect(status().isOk());
    }

    @Test
    void post() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(LETTER_ENDPOINT)
                        .content(GSON.toJson(buildRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(recommendationLetterService).create(AUTHOR, BODY);
    }

    private static String byId(String invalidId) {
        return LETTER_ENDPOINT.concat("/").concat(invalidId);
    }

    private static RecommendationLetterRequest buildRequest() {
        RecommendationLetterRequest request = new RecommendationLetterRequest();
        request.setAuthor(AUTHOR);
        request.setBody(BODY);
        return request;
    }
}
