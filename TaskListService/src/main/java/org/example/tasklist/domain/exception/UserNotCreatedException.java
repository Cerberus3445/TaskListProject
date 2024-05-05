package org.example.tasklist.domain.exception;

public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(String message) {
        super(message);
    }
}
