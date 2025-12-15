package com.mpb.wishlist.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Result of checking whether a product is in the wishlist")
public record WishlistContainsResponse(
        @Schema(example = "6899521ad2784646ae54aad0")
        String customerId,
        @Schema(example = "68ba4702f55b9b4816a6c622")
        String productId,
        @Schema(description = "Whether the product is present in the wishlist", example = "true")
        boolean contains) {
}
