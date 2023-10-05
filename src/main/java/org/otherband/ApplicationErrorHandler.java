package org.otherband;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.otherband.exceptions.UserInputException;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@ControllerAdvice
@Slf4j
public class ApplicationErrorHandler {
    @ExceptionHandler(UserInputException.class)
    ResponseEntity<?> handleUserError(UserInputException exception) {
        return handleError("User error", exception, badRequest());
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<?> handleInternalError(Throwable throwable) {
        return handleError("Something went wrong", throwable, internalServerError());
    }

    private static ResponseEntity<String> handleError(String logMessage, Throwable throwable, ResponseEntity.BodyBuilder bodyBuilder) {
        log.error(logMessage, throwable);
        return bodyBuilder.body(throwable.getMessage());
    }

}
