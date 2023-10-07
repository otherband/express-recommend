package org.otherband.email.simulation;

import org.otherband.Utils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.otherband.email.EmailService;
import org.otherband.email.VerificationLinkEmailRequest;
import java.util.UUID;
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
        emailsRepository.save(buildEmail(verificationLinkEmailRequest));
    }

    private static SimulatedEmail buildEmail(VerificationLinkEmailRequest verificationLinkEmailRequest) {
        SimulatedEmail simulatedEmail = new SimulatedEmail();
        simulatedEmail.setId(UUID.randomUUID().toString());
        simulatedEmail.setEmailTitle(String.format("Verification link for recommendation letter [%s]",
                verificationLinkEmailRequest.getLetterId()));
        simulatedEmail.setEmailBody(Utils.buildLetterVerificationLink(verificationLinkEmailRequest));
        simulatedEmail.setReceiverAddress(verificationLinkEmailRequest.getReceiverEmail());
        return simulatedEmail;
    }

}
