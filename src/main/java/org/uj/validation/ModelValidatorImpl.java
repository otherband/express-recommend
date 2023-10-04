package org.uj.validation;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.uj.exceptions.UserInputException;

public class ModelValidatorImpl implements ModelValidator {
    private final Validator validator;

    public ModelValidatorImpl() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            this.validator = validatorFactory.getValidator();
        }
    }

    @Override
    public void validate(Object model) throws UserInputException {
        try {
            validator.validate(model);
        } catch (ConstraintViolationException exception) {
            throw new UserInputException(exception.getMessage());
        }
    }
}
