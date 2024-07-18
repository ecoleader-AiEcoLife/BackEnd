package com.example.practice_eddy.exception.customException;

import com.example.practice_eddy.exception.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}