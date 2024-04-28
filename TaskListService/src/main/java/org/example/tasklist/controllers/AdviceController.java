package org.example.tasklist.controllers;

import org.example.tasklist.domain.exception.TaskNotFoundException;
import org.example.tasklist.domain.exception.UserNotFoundException;
import org.example.tasklist.util.ExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handlerException(UserNotFoundException userNotFoundException){
        ExceptionBody exceptionBody = new ExceptionBody(userNotFoundException.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handlerException(TaskNotFoundException taskNotFoundException){
        ExceptionBody exceptionBody = new ExceptionBody(taskNotFoundException.getMessage());
        return exceptionBody;
    }

}
