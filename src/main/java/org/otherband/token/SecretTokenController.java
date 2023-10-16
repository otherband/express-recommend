package org.otherband.token;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.otherband.token.SecretTokenController.TOKEN_ENDPOINT;

@RestController
@RequestMapping(TOKEN_ENDPOINT)
@CrossOrigin("*")
public class SecretTokenController {
    public static final String TOKEN_ENDPOINT = "/api/v1/verification";
    private final SecretTokenService tokenService;

    public SecretTokenController(SecretTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/create-token")
    @ResponseStatus(HttpStatus.CREATED)
    public void createToken(@RequestBody @Valid TokenRequest tokenRequest) {
        tokenService.create(tokenRequest.receiverEmail, tokenRequest.letterId);
    }

    @Data
    public static class TokenRequest {
        @NotBlank
        private String letterId;
        @Email
        private String receiverEmail;
    }
}
