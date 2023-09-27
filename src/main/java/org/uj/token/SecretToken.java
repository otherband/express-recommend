package org.uj.token;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SecretToken {
    private String tokenId;
    private String letterId;
    private String hashedSecret;
    private String associatedEmail;
    private LocalDateTime creationDate;
}
