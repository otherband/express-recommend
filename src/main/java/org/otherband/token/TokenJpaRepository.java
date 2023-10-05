package org.otherband.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, String> {
    List<TokenEntity> findByLetterId(String letterId);
}
