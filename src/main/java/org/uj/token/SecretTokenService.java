package org.uj.token;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.uj.email.EmailService;
import org.uj.email.VerificationLinkEmailRequest;
import org.uj.exceptions.UserInputException;
import org.uj.letter.RecommendationLetterRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.uj.Utils.validateNotBlank;

@Service
public class SecretTokenService {

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final RecommendationLetterRepository letterRepository;

    public SecretTokenService(EmailService emailService, TokenRepository tokenRepository, RecommendationLetterRepository letterRepository, PasswordEncoder passwordEncoder) {
        this.letterRepository = letterRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    public void verify(String tokenId, String letterId, String rawSecret) {
        validate(tokenId, letterId, rawSecret);
        getByLetterId(letterId)
                .stream()
                .filter(secretToken -> secretToken.getTokenId().equals(tokenId))
                .findAny()
                .filter(requestedToken -> passwordEncoder.matches(rawSecret, requestedToken.getHashedSecret()))
                .orElseThrow(() -> UserInputException.formatted("No matching secrets found for letter with ID [%s]", letterId));
    }

    public TokenEntity create(String receiverEmail, String letterId) {
        validate(receiverEmail, letterId);
        letterRepository.get(letterId).orElseThrow(() -> letterDoesNotExist(letterId));
        TokenEntity secretToken = new TokenEntity();
        secretToken.setCreationDate(LocalDateTime.now());
        secretToken.setLetterId(letterId);
        secretToken.setTokenId(randomString());
        secretToken.setAssociatedEmail(receiverEmail);
        handleSecret(receiverEmail, secretToken);
        tokenRepository.save(secretToken);
        return secretToken;
    }

    private static void validate(String receiverEmail, String letterId) {
        validateNotBlank(receiverEmail, "Email");
        validateNotBlank(letterId, "Letter ID");
    }

    protected void sendEmail(String receiverEmail, String secret, String tokenId, String letterId) {
        // provides a hook for testing
        VerificationLinkEmailRequest verificationLinkEmailRequest = new VerificationLinkEmailRequest();
        verificationLinkEmailRequest.setReceiverEmail(receiverEmail);
        verificationLinkEmailRequest.setSecretToken(secret);
        verificationLinkEmailRequest.setTokenId(tokenId);
        verificationLinkEmailRequest.setLetterId(letterId);
        emailService.sendLetterVerificationLink(verificationLinkEmailRequest);
    }

    private List<TokenEntity> getByLetterId(String letterId) {
        List<TokenEntity> associatedTokens = tokenRepository.getByLetterId(letterId);
        if (associatedTokens.isEmpty())
            throw UserInputException.formatted("No associated tokens found for letter with ID [%s]", letterId);
        return associatedTokens;
    }

    private static void validate(String tokenId, String letterId, String rawSecret) {
        validateNotBlank(tokenId, "Token ID");
        validateNotBlank(letterId, "Letter ID");
        validateNotBlank(rawSecret, "Secret");
    }

    private static UserInputException letterDoesNotExist(String letterId) {
        return UserInputException.formatted("Letter with ID [%s] does not exist", letterId);
    }

    private void handleSecret(String receiverEmail, TokenEntity secretToken) {
        String secret = randomString().concat(randomString());
        sendEmail(receiverEmail, secret, secretToken.getTokenId(), secretToken.getLetterId());
        secretToken.setHashedSecret(passwordEncoder.encode(secret));
    }

    private static String randomString() {
        return UUID.randomUUID().toString();
    }
}
