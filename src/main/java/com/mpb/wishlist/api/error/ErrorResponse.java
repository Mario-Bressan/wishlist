package com.mpb.wishlist.api.error;

import java.time.Instant;

public record ErrorResponse(Instant timestamp, int status, String error, String message) {
}
