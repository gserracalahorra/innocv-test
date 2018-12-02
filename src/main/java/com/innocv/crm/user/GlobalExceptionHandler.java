package com.innocv.crm.user;

import com.innocv.crm.user.exception.ResourceNotFoundException;
import com.innocv.crm.user.exception.ContentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value= HttpStatus.NO_CONTENT, reason = "Requested resource has no content")
    @ExceptionHandler(ContentNotFoundException.class)
    public void contentNotFound(ContentNotFoundException e) {
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Requested resource does not exist")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void resourceNotFound(ResourceNotFoundException e) {
    }

}