package com.mpb.wishlist.api.error;

import com.mpb.wishlist.exception.LimitExceededException;
import com.mpb.wishlist.exception.WishlistNotFoundException;
import jakarta.validation.ConstraintViolationException;
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
    public ResponseEntity<ErrorResponse> handleBodyValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildError(ErrorCode.VALIDATION_ERROR, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleParamsValidation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(v -> lastPath(v.getPropertyPath().toString()) + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return buildError(ErrorCode.VALIDATION_ERROR, message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return buildError(ErrorCode.VALIDATION_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unhandled exception", ex);
        return buildError(ErrorCode.INTERNAL_ERROR, null);
    }

    private static String lastPath(String path) {
        int idx = path.lastIndexOf('.');
        return (idx >= 0) ? path.substring(idx + 1) : path;
    }

    private ResponseEntity<ErrorResponse> buildError(ErrorCode code, String exceptionMessage) {
        HttpStatus status = code.getStatus();
        String message = exceptionMessage != null ? exceptionMessage : code.getDefaultMessage();

        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(),
                status.value(),
                code.getCode(),
                message
        ));
    }
}