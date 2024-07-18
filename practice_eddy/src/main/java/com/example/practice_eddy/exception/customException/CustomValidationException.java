package com.example.practice_eddy.exception.customException;

import com.example.practice_eddy.exception.ErrorCode;
import org.springframework.validation.BindingResult;

public class CustomValidationException extends CustomException {
    private final BindingResult bindingResult;

    public CustomValidationException(ErrorCode errorCode, BindingResult bindingResult) {
        super(errorCode);
        this.bindingResult = bindingResult;
    }

    public CustomValidationException(ErrorCode errorCode) {
        super(errorCode);
        this.bindingResult = null;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}