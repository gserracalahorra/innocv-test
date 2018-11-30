package com.innocv.crm.user.exception;


import com.fasterxml.jackson.core.JsonProcessingException;

public class MockException extends JsonProcessingException {

    public MockException() {
        super("Mock exception message");
    }

}