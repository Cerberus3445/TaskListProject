package org.example.tasklist.domain.exception;

public class TaskNotUpdatedException extends RuntimeException{

    public TaskNotUpdatedException(String message) {
        super(message);
    }
}
