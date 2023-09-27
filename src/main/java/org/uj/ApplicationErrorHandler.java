package org.uj;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.uj.exceptions.UserInputException;

@ControllerAdvice
public class ApplicationErrorHandler {
    @ExceptionHandler(UserInputException.class)
    ResponseEntity<?> handleIllegalArgumentException(UserInputException exception) {
        return badRequest(exception);
    }

    private static ResponseEntity<String> badRequest(UserInputException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
