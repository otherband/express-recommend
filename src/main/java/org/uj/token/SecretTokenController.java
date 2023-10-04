package org.uj.token;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.uj.token.SecretTokenController.TOKEN_ENDPOINT;

@RestController
@RequestMapping(TOKEN_ENDPOINT)
public class SecretTokenController {
    public static final String TOKEN_ENDPOINT = "/api/v1/verification";
    private final SecretTokenService tokenService;

    public SecretTokenController(SecretTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/create-token")
    @ResponseStatus(HttpStatus.CREATED)
    public void createToken(@RequestBody TokenRequest tokenRequest) {
        tokenService.create(tokenRequest.receiverEmail, tokenRequest.letterId);
    }

    @PostMapping("/{letterId}/{tokenId}/{secret}")
    public void verify(@PathVariable String letterId, @PathVariable String tokenId, @PathVariable String secret) {
        tokenService.verify(tokenId, letterId, secret);
    }

    @Data
    public static class VerificationRequest {
        private String letterId;
        private String tokenId;
        private String secretToken;
    }

    @Data
    public static class TokenRequest {
        private String letterId;
        private String receiverEmail;
    }
}
