package com.innocv.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExceptionResponse {

    private Class exception;

    private String message;

    public static UserExceptionResponse create(Class exceptionClass, String message) {
        return new UserExceptionResponse(exceptionClass, message);
    }

}