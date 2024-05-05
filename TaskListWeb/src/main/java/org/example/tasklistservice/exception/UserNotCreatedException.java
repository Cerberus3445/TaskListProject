package org.example.tasklistservice.exception;

public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(String message) {
        super(message);
    }
}
