package com.innocv.crm.user.exception;

public class InternalServerException extends RuntimeException {

    private Class exceptionType;

    private String message;

    public InternalServerException() {

    }

    public InternalServerException(Class exceptionType, String message) {
        this.exceptionType = exceptionType;
        this.message = message;
    }

}