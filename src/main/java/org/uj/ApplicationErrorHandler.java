package org.uj;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.uj.exceptions.UserInputException;

import java.awt.*;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.internalServerError;

@ControllerAdvice
public class ApplicationErrorHandler {
    @ExceptionHandler(UserInputException.class)
    ResponseEntity<?> handleUserError(UserInputException exception) {
        return badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleInternalError(Exception exception) {
        return internalServerError().body("Something went wrong");
    }

}
