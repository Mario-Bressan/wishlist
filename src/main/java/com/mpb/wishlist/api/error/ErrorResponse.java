package com.mpb.wishlist.api.error;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Standard error response")
public record ErrorResponse(
        @Schema(description = "Timestamp when the error ocurred (UTC)",
                example = "2025-12-15T11:22:21.689994300Z")
        Instant timestamp,
        @Schema(
                description = "HTTP status code",
                example = "404"
        )
        int status,
        @Schema(
                description = "Application error code",
                example = "WISHLIST_NOT_FOUND"
        )
        String error,
        @Schema(
                description = "Human-readable error message",
                example = "Wishlist not found for customerId: customer-1"
        )
        String message) {
}
