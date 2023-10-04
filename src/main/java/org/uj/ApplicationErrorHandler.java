package org.uj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.uj.exceptions.UserInputException;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@ControllerAdvice
@Slf4j
public class ApplicationErrorHandler {
    @ExceptionHandler(UserInputException.class)
    ResponseEntity<?> handleUserError(UserInputException exception) {
        log.error("User error: " + exception);
        return badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<?> handleInternalError(Throwable throwable) {
        log.error("Something went wrong: " + throwable);
        return internalServerError().body("Something went wrong");
    }

}
