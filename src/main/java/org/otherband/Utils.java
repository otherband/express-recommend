package org.otherband;

import org.otherband.email.VerificationLinkEmailRequest;
import org.otherband.exceptions.UserInputException;
import org.otherband.letter.RecommendationLetterController;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class Utils {

    private Utils() {
    }

    public static void validateNotBlank(String field, String fieldName) {
        if (isBlank(field))
            throw UserInputException.formatted("[%s] cannot be blank", fieldName);
    }

    public static String buildLetterVerificationLink(VerificationLinkEmailRequest verificationLinkEmailRequest) {
        return linkTo(methodOn(RecommendationLetterController.class)
                .verify(verificationLinkEmailRequest.getLetterId(),
                        verificationLinkEmailRequest.getTokenId(),
                        verificationLinkEmailRequest.getSecretToken())).withSelfRel().toString();
    }
}
