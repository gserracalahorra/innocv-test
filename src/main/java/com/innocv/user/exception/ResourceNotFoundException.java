package com.innocv.user.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    private String id;




}