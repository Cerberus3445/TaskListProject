package org.example.tasklist.controllers;

import org.example.tasklist.domain.exception.*;
import org.example.tasklist.util.ExceptionBody;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(TaskNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(TaskNotCreatedException taskNotCreatedException){
        ExceptionBody exceptionBody = new ExceptionBody(taskNotCreatedException.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler(TaskNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(TaskNotUpdatedException taskNotUpdatedException){
        ExceptionBody exceptionBody = new ExceptionBody(taskNotUpdatedException.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler(UserNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(UserNotCreatedException userNotCreatedException){
        ExceptionBody exceptionBody = new ExceptionBody(userNotCreatedException.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler(UserNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(UserNotUpdatedException userNotUpdatedException){
        ExceptionBody exceptionBody = new ExceptionBody(userNotUpdatedException.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler(PasswordNotValid.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(PasswordNotValid passwordNotValid){
        ExceptionBody exceptionBody = new ExceptionBody(passwordNotValid.getMessage());
        return exceptionBody;
    }
}
