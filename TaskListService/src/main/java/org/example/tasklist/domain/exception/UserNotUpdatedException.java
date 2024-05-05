package org.example.tasklist.domain.exception;

public class UserNotUpdatedException extends RuntimeException{

    public UserNotUpdatedException(String message) {
        super(message);
    }
}
