package org.example.tasklistservice.exception;

public class QuoteNotFoundException extends RuntimeException{

    public QuoteNotFoundException(String message) {
        super(message);
    }
}
