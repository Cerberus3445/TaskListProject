package org.example.tasklist.domain.exception;

public class PasswordNotValid extends RuntimeException{

    public PasswordNotValid(String message) {
        super(message);
    }
}
