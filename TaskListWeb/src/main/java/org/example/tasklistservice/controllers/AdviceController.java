package org.example.tasklistservice.controllers;

import org.example.tasklistservice.exception.ServerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ServerException.class)
    public ModelAndView handlerException(ServerException serverException){
        return new ModelAndView("errors/503");
    }
}
