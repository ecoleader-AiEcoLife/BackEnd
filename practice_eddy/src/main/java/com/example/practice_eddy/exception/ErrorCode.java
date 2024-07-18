package com.example.practice_eddy.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 400 BAD_REQUEST
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Invalid input value"),
    INVALID_TYPE_NAME(HttpStatus.BAD_REQUEST, "Invalid type name"),
    MALFORMED_JSON_REQUEST(HttpStatus.BAD_REQUEST, "Malformed JSON request"),

    // 401 UNAUTHORIZED
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized access"),

    // 403 FORBIDDEN
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied"),

    // 404 NOT_FOUND
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "Type not found"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Board not found"),

    // 409 CONFLICT
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "Resource already exists"),
    DUPLICATE_TYPE(HttpStatus.CONFLICT, "Type already exists"),
    DUPLICATE_BOARD(HttpStatus.CONFLICT, "Board already exists"),

    // 422 UNPROCESSABLE_ENTITY
    BUSINESS_RULE_VIOLATION(HttpStatus.UNPROCESSABLE_ENTITY, "Business rule violation"),

    // 500 INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),

    // 503 SERVICE_UNAVAILABLE
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "Service temporarily unavailable");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}