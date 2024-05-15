package com.example.auth.controller;

import com.example.auth.exception.ExistedUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ExistedUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleExistedUserException(ExistedUserException e){
        log.debug(e.getMessage());
        return e.getMessage();
    }
}
