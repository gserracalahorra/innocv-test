package com.innocv.crm.user;

import com.innocv.crm.user.exception.InternalServerException;
import com.innocv.crm.user.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalHandler {

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Requested resource does not exist")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void resourceNotFound(ResourceNotFoundException e) {
    }

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason = "No ")
    @ExceptionHandler(InternalServerException.class)
    public void resourceNotFound(InternalServerException e) {
    }

}