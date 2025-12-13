package com.mpb.wishlist.api.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    WISHLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Wishlist not found"),
    WISHLIST_LIMIT_EXCEEDED(HttpStatus.CONFLICT, "Wishlist limit exceeded"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getCode() {
        return name();
    }
}
