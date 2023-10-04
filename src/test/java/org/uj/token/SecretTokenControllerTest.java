package org.uj.token;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.uj.BaseApplicationTest;
import org.uj.email.EmailService;
import org.uj.letter.RecommendationLetter;
import org.uj.letter.RecommendationLetterRepository;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.uj.token.SecretTokenController.*;
import static org.uj.token.SecretTokenController.TOKEN_ENDPOINT;

public class SecretTokenControllerTest extends BaseApplicationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private RecommendationLetterRepository letterRepository;

    @Test
    public void createAndUtilize() throws Exception {
        mockMvc.perform(
                post(TOKEN_ENDPOINT.concat("/create-token"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(GSON.toJson(buildRequest()))
        ).andExpect(status().isCreated());

    }

    private TokenRequest buildRequest() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setReceiverEmail("yazan@yaz.com");
        String letterId = UUID.randomUUID().toString();
        tokenRequest.setLetterId(letterId);
        letterRepository.save(buildLetter(letterId));
        return tokenRequest;
    }

    private static RecommendationLetter buildLetter(String letterId) {
        RecommendationLetter letter = new RecommendationLetter();
        letter.setAuthor("Yazan");
        letter.setBody("Yazan is splendid");
        letter.setId(letterId);
        letter.setValidated(false);
        return letter;
    }


}
