package com.mpb.wishlist.api.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Represents a customer's wishlist")
public record WishlistResponse(
        @Schema(
                description = "Customer unique identifier",
                example = "6899521ad2784646ae54aad0"
        )
        String customerId,
        @ArraySchema(
                schema = @Schema(
                        description = "Product id in the wishlist",
                        example = "68ba4702f55b9b4816a6c622"
                ),
                arraySchema = @Schema(
                        description = "List of product ids in the wishlist"
                )
        )
        List<String> productIds) {
}
