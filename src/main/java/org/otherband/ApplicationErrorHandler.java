package org.otherband;

import lombok.extern.slf4j.Slf4j;
import org.otherband.exceptions.UserInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@ControllerAdvice
@Slf4j
public class ApplicationErrorHandler {
    @ExceptionHandler(UserInputException.class)
    ResponseEntity<?> handleUserError(UserInputException exception) {
        return handleError(exception.getMessage(), exception, badRequest());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleViolation(MethodArgumentNotValidException exception) {
        return handleError(formatMessage(exception), exception, badRequest());
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<?> handleInternalError(Throwable throwable) {
        return handleError("Something went wrong", throwable, internalServerError());
    }

    private static ResponseEntity<String> handleError(String message, Throwable throwable, ResponseEntity.BodyBuilder bodyBuilder) {
        log.error("Error occurred: ", throwable);
        return bodyBuilder.body(message);
    }

    private static String formatMessage(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getFieldError();
        return String.format("Invalid value for field [%s]. Value: [%s]. Reason: [%s]",
                fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

}
