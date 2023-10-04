package org.uj.token;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.uj.email.EmailService;
import org.uj.exceptions.UserInputException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SecretTokenService {

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    public SecretTokenService(PasswordEncoder passwordEncoder, EmailService emailService, TokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    public void verify(String tokenId, String letterId, String rawSecret) {
        getByLetterId(letterId)
                .stream()
                .filter(secretToken -> secretToken.getTokenId().equals(tokenId))
                .findAny()
                .filter(requestedToken -> passwordEncoder.matches(rawSecret, requestedToken.getHashedSecret()))
                .orElseThrow(() -> formattedException("No matching secrets found for letter with ID [%s]", letterId));
    }

    private List<SecretToken> getByLetterId(String letterId) {
        List<SecretToken> associatedTokens = tokenRepository.getByLetterId(letterId);
        if (associatedTokens.isEmpty())
            throw formattedException("No associated tokens found for letter with ID [%s]", letterId);
        return associatedTokens;
    }

    private static UserInputException formattedException(String template, String letterId) {
        return new UserInputException(String.format(template, letterId));
    }

    public SecretToken create(String receiverEmail, String letterId) {
        SecretToken secretToken = new SecretToken();
        secretToken.setCreationDate(LocalDateTime.now());
        secretToken.setLetterId(letterId);
        secretToken.setTokenId(randomString());
        secretToken.setAssociatedEmail(receiverEmail);
        handleSecret(receiverEmail, secretToken, secretToken.getTokenId());
        tokenRepository.save(secretToken);
        return secretToken;
    }

    private void handleSecret(String receiverEmail, SecretToken secretToken, String tokenId) {
        String secret = randomString().concat(randomString());
        sendEmail(receiverEmail, secret, tokenId);
        secretToken.setHashedSecret(passwordEncoder.encode(secret));
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }

    protected void sendEmail(String receiverEmail, String secret, String tokenId) {
        // provides a hook for testing
        emailService.sendEmail(receiverEmail,
                "Verification link for recommendation letter",
                secret);
    }
}
