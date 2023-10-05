package org.uj.token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    TokenEntity save(TokenEntity secretToken);

    List<TokenEntity> getByLetterId(String letterId);

    Optional<TokenEntity> getByTokenId(String tokenId);

}
