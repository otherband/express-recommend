package org.uj.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SecretToken {
    @Id
    private String tokenId;
    @Column(nullable = false)
    private String letterId;
    @Column(nullable = false)
    private String hashedSecret;
    @Column(nullable = false)
    private String associatedEmail;
    @Column(nullable = false)
    private LocalDateTime creationDate;
}
