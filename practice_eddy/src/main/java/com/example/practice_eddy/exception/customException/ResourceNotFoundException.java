package com.example.practice_eddy.exception.customException;

import com.example.practice_eddy.exception.ErrorCode;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(ErrorCode.RESOURCE_NOT_FOUND,
            String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}