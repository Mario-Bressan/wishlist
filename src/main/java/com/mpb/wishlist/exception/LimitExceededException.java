package com.mpb.wishlist.exception;

public class LimitExceededException extends RuntimeException {
    public LimitExceededException(String customerId, int maxItems) {
        super("Customer %s wishlist reached the limit %s products".formatted(customerId, maxItems));
    }
}