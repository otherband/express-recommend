package org.uj;

import com.google.gson.Gson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.uj.email.EmailService;
import org.uj.token.SecretTokenServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class BaseApplicationTest {
    protected static final Gson GSON = new Gson();

    @Configuration
    static class TestConfiguration {
        @Bean
        EmailService getEmailService() {
            return new SecretTokenServiceTest.FakeEmailService();
        }
    }

}
