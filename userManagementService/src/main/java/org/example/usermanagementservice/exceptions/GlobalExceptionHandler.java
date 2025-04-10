package org.example.usermanagementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse("User Not Found", ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Default to 500

        if (message != null && message.contains("CONFLICT")) {
            status = HttpStatus.CONFLICT;
        } else if (message != null && message.contains("FORBIDDEN")) {
            status = HttpStatus.FORBIDDEN;
        }
        else if(message != null && message.contains("BAD_REQUEST")) {
            status = HttpStatus.BAD_REQUEST;
        }
        else if(message != null && message.contains("NOT_FOUND")) {
            status = HttpStatus.NOT_FOUND;
        }

        return ResponseEntity.status(status)
                .body(Map.of(
                        "error", "An unexpected error occurred: " + message,
                        "status", String.valueOf(status.value()),
                        "message", status.getReasonPhrase()
                ));

    }

    @ExceptionHandler(OtpOrTokenExpiredException.class)
    public ResponseEntity<String> handleTokenExpired(OtpOrTokenExpiredException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
