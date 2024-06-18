package org.example.tasklist.domain.exception;

public class QuoteNotFoundException extends RuntimeException{

    public QuoteNotFoundException(String message) {
        super(message);
    }
}
