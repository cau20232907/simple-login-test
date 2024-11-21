package com.hemiplegia.server.controller;

import com.hemiplegia.server.exception.UsernameAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public String fileNotFoundHandler(UsernameAlreadyInUseException e) {
        return e.getMessage();
    }
}