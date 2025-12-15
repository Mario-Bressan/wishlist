package com.mpb.wishlist.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for adding a product to the wishlist")
public record WishlistItemRequest(
        @NotBlank
        @Schema(
                description = "Unique identifier of the product in the catalog",
                example = "68ba4702f55b9b4816a6c622"
        )
        String productId) {
}
