package com.mpb.wishlist.api.error;

import com.mpb.wishlist.exception.LimitExceededException;
import com.mpb.wishlist.exception.WishlistNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWishlistNotFound(WishlistNotFoundException ex) {
        log.warn("WishlistNotFoundException - {}", ex.getMessage());
        return buildError(ErrorCode.WISHLIST_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleLimitExceeded(LimitExceededException ex) {
        log.warn("LimitExceededException - {}", ex.getMessage());
        return buildError(ErrorCode.WISHLIST_LIMIT_EXCEEDED, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.warn("Validation error - {}", message);
        return buildError(ErrorCode.VALIDATION_ERROR, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unhandled exception - {}", ex.getMessage(), ex);
        return buildError(ErrorCode.INTERNAL_ERROR, null);
    }

    private ResponseEntity<ErrorResponse> buildError(ErrorCode code, String exceptionMessage) {
        HttpStatus status = code.getStatus();
        String message = exceptionMessage != null ? exceptionMessage : code.getDefaultMessage();

        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                status.value(),
                code.getCode(),
                message
        );

        return ResponseEntity.status(status).body(body);
    }

}