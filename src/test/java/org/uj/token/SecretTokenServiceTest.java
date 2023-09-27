package org.uj.token;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SecretTokenServiceTest {
    private SecretTokenService tokenService = new SecretTokenService();

    @Test
    void create() {
        SecretToken secretToken = tokenService.create();
        assertAlmostNow(secretToken.getCreationDate());
    }

    private void assertAlmostNow(LocalDateTime creationDate) {
        assertThat(Duration.between(creationDate, LocalDateTime.now()).toMillis())
                .isLessThan(100);
    }

}
