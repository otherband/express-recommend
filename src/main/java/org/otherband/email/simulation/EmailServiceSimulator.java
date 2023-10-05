package org.otherband.email.simulation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.otherband.email.EmailService;
import org.otherband.email.VerificationLinkEmailRequest;
import org.otherband.letter.RecommendationLetterController;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.otherband.email.simulation.SimulatedEmailsRepository.SimulatedEmail;

@Component
@Profile("dev")
public class EmailServiceSimulator implements EmailService {
    private final SimulatedEmailsRepository emailsRepository;

    public EmailServiceSimulator(SimulatedEmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    @Override
    public void sendLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest) {
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
        return linkTo(methodOn(RecommendationLetterController.class)
                .verify(letterId, tokenId, secretToken)).withSelfRel().toString();
    }
}
