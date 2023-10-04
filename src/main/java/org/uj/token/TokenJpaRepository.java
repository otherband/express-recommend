package org.uj.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenJpaRepository extends JpaRepository<SecretToken, String> {
    List<SecretToken> findByLetterId(String letterId);
}
