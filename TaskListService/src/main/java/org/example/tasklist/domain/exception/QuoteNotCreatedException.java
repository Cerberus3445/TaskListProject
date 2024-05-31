package org.example.tasklist.domain.exception;

public class QuoteNotCreatedException extends RuntimeException{

    public QuoteNotCreatedException(String message) {
        super(message);
    }
}
