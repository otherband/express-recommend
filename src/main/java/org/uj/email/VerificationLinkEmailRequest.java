package org.uj.email;

import lombok.Data;

@Data
public class VerificationLinkEmailRequest {
    private String receiverEmail;
    private String secretToken;
    private String tokenId;
    private String letterId;
}
