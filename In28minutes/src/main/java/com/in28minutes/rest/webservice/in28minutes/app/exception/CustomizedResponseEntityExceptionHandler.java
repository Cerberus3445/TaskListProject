package com.in28minutes.rest.webservice.in28minutes.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handler(UserNotFoundException userNotFoundException){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), userNotFoundException.getMessage(), null);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handler(Exception e){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), null);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        for(FieldError fieldError : ex.getFieldErrors()){
            stringBuilder.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage());
        }
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), stringBuilder.toString(), null);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
