package org.uj.token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    void save(SecretToken secretToken);

    List<SecretToken> getByLetterId(String letterId);

    Optional<SecretToken> getByTokenId(String tokenId);

}
