package org.uj.email.simulation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.uj.email.EmailRequest;
import org.uj.email.EmailService;

import static org.uj.email.simulation.SimulatedEmailsRepository.SimulatedEmail;

@Component
@Profile("dev")
public class EmailServiceSimulator implements EmailService {
    private final SimulatedEmailsRepository emailsRepository;

    public EmailServiceSimulator(SimulatedEmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    @Override
    public void sendLetterVerificationLink(EmailRequest emailRequest) {
        emailsRepository.save(buildEmail(emailRequest.getLetterId(),
                emailRequest.getTokenId(),
                emailRequest.getSecretToken(),
                emailRequest.getReceiverEmail()));
    }

    private static SimulatedEmail buildEmail(String letterId, String tokenId, String secretToken, String receiverAddress) {
        SimulatedEmail simulatedEmail = new SimulatedEmail();
        simulatedEmail.setEmailTitle(String.format("Verification link for recommendation letter [%s]", letterId));
        simulatedEmail.setEmailBody(buildLink(letterId, tokenId, secretToken));
        simulatedEmail.setReceiverAddress(receiverAddress);
        return simulatedEmail;
    }

    private static String buildLink(String letterId, String tokenId, String secretToken) {
        return String.format("http://localhost:8085/api/v1/verification/%s/%s/%s", letterId, tokenId, secretToken);
    }
}
