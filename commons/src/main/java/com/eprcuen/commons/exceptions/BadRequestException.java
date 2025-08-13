package com.eprcuen.commons.exceptions;

import lombok.Getter;

import java.util.List;

/**
 * Custom exception class for handling bad requests in the application.
 * This exception is thrown when the request contains invalid data or parameters.
 * It encapsulates a list of error messages to provide detailed feedback.
 *
 * @author caito
 *
 */
@Getter
public class BadRequestException extends RuntimeException {
    private final List<String> messages;
    public BadRequestException(List<String> messages) {
        this.messages = messages;
    }
}
