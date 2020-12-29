package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.exception.ErrorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class ExceptionHandlerController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView showBadRequestError(NumberFormatException e){
        ModelAndView model= new ModelAndView();
        model.setViewName("errors/error400");
        model.addObject("exception", e);
        return model;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ErrorNotFoundException.class)
    public ModelAndView showNotFoundError(RuntimeException e){
        ModelAndView model= new ModelAndView();
        model.setViewName("errors/error404");
        model.addObject("exception", e);
        return model;
    }

}
