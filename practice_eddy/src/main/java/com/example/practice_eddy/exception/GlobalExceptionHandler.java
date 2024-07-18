package com.example.practice_eddy.exception;

import com.example.practice_eddy.exception.customException.CustomException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, WebRequest request) {
        return createErrorResponse(ex.getErrorCode(), request, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, request.getDescription(false));

        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors()
                .forEach(error -> errorResponse.addFieldError(error.getField(), error.getDefaultMessage()));
        } else {
            ((ConstraintViolationException) ex).getConstraintViolations()
                .forEach(violation -> errorResponse.addFieldError(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex, WebRequest request) {
        return createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, request, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode, WebRequest request, String message) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode, request.getDescription(false), message);
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}