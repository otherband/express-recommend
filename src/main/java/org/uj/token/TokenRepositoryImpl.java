package org.uj.token;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJpaRepository tokenJpaRepository;

    public TokenRepositoryImpl(TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public TokenEntity save(TokenEntity secretToken) {
        return tokenJpaRepository.save(secretToken);
    }

    @Override
    public List<TokenEntity> getByLetterId(String letterId) {
        return tokenJpaRepository.findByLetterId(letterId);
    }

    @Override
    public Optional<TokenEntity> getByTokenId(String tokenId) {
        return tokenJpaRepository.findById(tokenId);
    }
}
