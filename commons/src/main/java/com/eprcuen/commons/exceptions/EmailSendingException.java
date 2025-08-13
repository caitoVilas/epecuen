package com.eprcuen.commons.exceptions;

/**
 * Custom exception to handle errors related to email sending.
 * This exception is thrown when there is an issue with sending an email.
 *
 * @author caito
 *
 */
public class EmailSendingException extends RuntimeException{
    public EmailSendingException(String message) {
        super(message);
    }
}
