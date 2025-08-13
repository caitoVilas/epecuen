package com.eprcuen.commons.exceptions;

/**
 * Custom exception to handle errors related to file I/O operations.
 * This exception is thrown when there is an issue with reading or writing files.
 *
 * @author caito
 *
 */
public class FileIOException extends RuntimeException{
    public FileIOException(String message) {
        super(message);
    }
}
