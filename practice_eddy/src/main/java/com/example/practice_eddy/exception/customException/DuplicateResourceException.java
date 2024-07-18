package com.example.practice_eddy.exception.customException;

import com.example.practice_eddy.exception.ErrorCode;

public class DuplicateResourceException extends CustomException {

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(ErrorCode.DUPLICATE_RESOURCE,
            String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}