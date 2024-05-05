package org.example.tasklistservice.exception;

public class TaskNotUpdatedException extends RuntimeException{

    public TaskNotUpdatedException(String message) {
        super(message);
    }
}
