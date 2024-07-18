package com.example.practice_eddy.exception.customException;

import com.example.practice_eddy.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}