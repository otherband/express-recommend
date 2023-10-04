package org.uj.exceptions;

public class UserInputException extends RuntimeException {
    public UserInputException(String message) {
        super(message);
    }

    public static UserInputException formatted(String format, Object... args) {
        return new UserInputException(String.format(format, args));
    }
}
