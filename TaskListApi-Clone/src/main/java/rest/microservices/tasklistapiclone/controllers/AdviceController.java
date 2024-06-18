package rest.microservices.tasklistapiclone.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rest.microservices.tasklistapiclone.domain.exception.*;
import rest.microservices.tasklistapiclone.util.ExceptionBody;

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

    @ExceptionHandler(TaskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(TaskException taskException){
        return new ExceptionBody(taskException.getMessage());
    }


    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(UserException userException){
        return new ExceptionBody(userException.getMessage());
    }

    @ExceptionHandler(QuoteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlerException(QuoteException quoteException){
        return new ExceptionBody(quoteException.getMessage());
    }
}
