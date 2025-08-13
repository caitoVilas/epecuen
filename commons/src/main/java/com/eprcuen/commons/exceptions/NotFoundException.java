package com.eprcuen.commons.exceptions;

/**
 * Custom exception to indicate that a requested resource was not found.
 * This exception can be thrown when an entity or resource is not found in the system.
 *
 * @author caito
 *
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
