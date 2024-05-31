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
        return new ExceptionBody(userNotFoundException.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handlerException(TaskNotFoundException taskNotFoundException){
        return new ExceptionBody(taskNotFoundException.getMessage());
    }

    @ExceptionHandler(TaskNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(TaskNotCreatedException taskNotCreatedException){
        return new ExceptionBody(taskNotCreatedException.getMessage());
    }

    @ExceptionHandler(TaskNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(TaskNotUpdatedException taskNotUpdatedException){
        return new ExceptionBody(taskNotUpdatedException.getMessage());
    }

    @ExceptionHandler(UserNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(UserNotCreatedException userNotCreatedException){
        return new ExceptionBody(userNotCreatedException.getMessage());
    }

    @ExceptionHandler(UserNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(UserNotUpdatedException userNotUpdatedException){
        return new ExceptionBody(userNotUpdatedException.getMessage());
    }

    @ExceptionHandler(PasswordNotValid.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(PasswordNotValid passwordNotValid){
        return new ExceptionBody(passwordNotValid.getMessage());
    }

    @ExceptionHandler(QuoteNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(QuoteNotCreatedException quoteNotCreatedException){
        return new ExceptionBody(quoteNotCreatedException.getMessage());
    }

    @ExceptionHandler(QuoteNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(QuoteNotUpdatedException quoteNotUpdatedException){
        return new ExceptionBody(quoteNotUpdatedException.getMessage());
    }
}
