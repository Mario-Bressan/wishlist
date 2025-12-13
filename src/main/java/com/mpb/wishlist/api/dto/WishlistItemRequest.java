package com.mpb.wishlist.api.dto;

import jakarta.validation.constraints.NotBlank;

public record WishlistItemRequest(@NotBlank String productId) {}
