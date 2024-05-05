package org.example.tasklistservice.exception;

public class TaskNotCreatedException extends RuntimeException{

    public TaskNotCreatedException(String message) {
        super(message);
    }
}
