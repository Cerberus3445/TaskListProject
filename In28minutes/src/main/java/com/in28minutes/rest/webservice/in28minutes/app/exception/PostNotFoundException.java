package com.in28minutes.rest.webservice.in28minutes.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(String message) {
        super(message);
    }
}