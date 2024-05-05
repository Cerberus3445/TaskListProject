package org.example.tasklist.domain.exception;

public class TaskNotCreatedException extends RuntimeException{

    public TaskNotCreatedException(String message) {
        super(message);
    }
}
