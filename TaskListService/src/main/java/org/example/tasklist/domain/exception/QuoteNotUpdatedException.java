package org.example.tasklist.domain.exception;

public class QuoteNotUpdatedException extends RuntimeException{

    public QuoteNotUpdatedException(String message) {
        super(message);
    }
}
