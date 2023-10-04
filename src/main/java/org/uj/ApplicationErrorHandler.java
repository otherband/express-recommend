package org.uj;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.uj.exceptions.UserInputException;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@ControllerAdvice
public class ApplicationErrorHandler {
    @ExceptionHandler(UserInputException.class)
    ResponseEntity<?> handleUserError(UserInputException exception) {
        return badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<?> handleInternalError(Throwable exception) {
        return internalServerError().body("Something went wrong");
    }

}
