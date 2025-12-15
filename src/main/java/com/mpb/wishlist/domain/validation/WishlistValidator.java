package com.mpb.wishlist.domain.validation;

import java.util.Objects;

public final class WishlistValidator {

    public static final int MAX_ID_LENGTH = 64;

    private WishlistValidator() {
    }

    public static String requireCustomerId(String value) {
        return requireId(value, "customerId");
    }

    public static String requireProductId(String value) {
        return requireId(value, "productId");
    }

    private static String requireId(String value, String field) {
        Objects.requireNonNull(field, "field");

        if (value == null) {
            throw new IllegalArgumentException(field + " cannot be null");
        }

        String normalized = value.trim();

        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }
        if (normalized.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException(field + " must be at most " + MAX_ID_LENGTH + " chars");
        }
        if (!isAllowedId(normalized)) {
            throw new IllegalArgumentException(field + " must be alphanumeric and may contain '-' or '_'");
        }
        return normalized;
    }

    private static boolean isAllowedId(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '-' || c == '_')) {
                return false;
            }
        }
        return true;
    }
}