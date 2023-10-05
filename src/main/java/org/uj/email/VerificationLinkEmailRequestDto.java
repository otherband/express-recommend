package org.uj.email;

import lombok.Data;

@Data
public class VerificationLinkEmailRequestDto {
    private String receiverEmail;
    private String secretToken;
    private String tokenId;
    private String letterId;
}
