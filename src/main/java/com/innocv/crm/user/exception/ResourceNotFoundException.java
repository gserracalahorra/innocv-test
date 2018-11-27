package com.innocv.crm.user.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    private String userId;

    public ResourceNotFoundException(String userId) {
        this.userId = userId;
    }

}