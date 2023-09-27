package org.uj.token;

import java.time.LocalDateTime;

public class SecretTokenService {

    public SecretToken create() {
        SecretToken secretToken = new SecretToken();
        secretToken.setCreationDate(LocalDateTime.now());
        return secretToken;
    }
}
