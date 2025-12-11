package com.mpb.wishlist.exception;

public class WishlistNotFoundException extends RuntimeException {

    public WishlistNotFoundException(String customerId) {
        super("Wishlist not found for customerId: %s".formatted(customerId));
    }
}
