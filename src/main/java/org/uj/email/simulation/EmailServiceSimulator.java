package org.uj.email.simulation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.uj.email.VerificationLinkEmailRequestDto;
import org.uj.email.EmailService;

import java.util.UUID;

import static org.uj.email.simulation.SimulatedEmailsRepository.SimulatedEmail;

@Component
@Profile("dev")
public class EmailServiceSimulator implements EmailService {
    private final SimulatedEmailsRepository emailsRepository;

    public EmailServiceSimulator(SimulatedEmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    @Override
    public void sendLetterVerificationLink(VerificationLinkEmailRequestDto verificationLinkEmailRequest) {
        emailsRepository.save(buildEmail(verificationLinkEmailRequest.getLetterId(),
                verificationLinkEmailRequest.getTokenId(),
                verificationLinkEmailRequest.getSecretToken(),
                verificationLinkEmailRequest.getReceiverEmail()));
    }

    private static SimulatedEmail buildEmail(String letterId, String tokenId, String secretToken, String receiverAddress) {
        SimulatedEmail simulatedEmail = new SimulatedEmail();
        simulatedEmail.setId(UUID.randomUUID().toString());
        simulatedEmail.setEmailTitle(String.format("Verification link for recommendation letter [%s]", letterId));
        simulatedEmail.setEmailBody(buildLink(letterId, tokenId, secretToken));
        simulatedEmail.setReceiverAddress(receiverAddress);
        return simulatedEmail;
    }

    private static String buildLink(String letterId, String tokenId, String secretToken) {
        return String.format("http://localhost:8085/api/v1/verification/%s/%s/%s", letterId, tokenId, secretToken);
    }
}
