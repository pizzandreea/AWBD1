package com.project.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandles {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handlerNotFoundException(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("exception", exception);
        modelAndView.setViewName("notFoundException");
        return modelAndView;
    }
}
