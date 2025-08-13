package com.eprcuen.commons.exceptions;

/* * UnauthorizedException.java
 * This class represents an exception that is thrown when a user is not authorized to access a resource.
 * It extends RuntimeException, allowing it to be thrown without being caught or declared in method signatures.
 *
 * @author caito
 *
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
