package com.example.takeout.controller;

import com.example.takeout.exception.ArgumentCheckException;
import com.example.takeout.exception.ServiceException;
import com.example.takeout.util.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ServiceException.class})
    public Result<?> serviceExceptionHandler(ServiceException e) {
        return Result.builder().code(e.getCode()).msg(e.getMsg()).data(null).build();
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result<?> argumentCheckExceptionHandler() {
        ArgumentCheckException argumentCheckException = new ArgumentCheckException();
        return Result.builder().code(argumentCheckException.getCode()).msg(argumentCheckException.getMsg()).data(null).build();
    }
}