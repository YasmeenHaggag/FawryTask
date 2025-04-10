package org.example.usermanagementservice.exceptions;

public class OtpOrTokenExpiredException extends RuntimeException {
    public OtpOrTokenExpiredException(String message) {
        super(message);
    }
}
