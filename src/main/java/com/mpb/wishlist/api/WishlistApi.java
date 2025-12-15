package com.mpb.wishlist.api;

import com.mpb.wishlist.api.dto.WishlistContainsResponse;
import com.mpb.wishlist.api.dto.WishlistItemRequest;
import com.mpb.wishlist.api.dto.WishlistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Wishlist", description = "Operations for managing a customer's wishlist")
@RequestMapping("/wishlist")
public interface WishlistApi {

    @Operation(summary = "Add product to wishlist")
    @ApiResponse(
            responseCode = "200",
            description = "Wishlist updated",
            content = @Content(schema = @Schema(implementation = WishlistResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Wishlist not found")
    @PostMapping("/{customerId}")
    ResponseEntity<WishlistResponse> addProduct(
            @PathVariable String customerId,
            @Valid @RequestBody WishlistItemRequest request
    );

    @Operation(summary = "Remove product from wishlist")
    @ApiResponse(
            responseCode = "200",
            description = "Wishlist updated",
            content = @Content(schema = @Schema(implementation = WishlistResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Wishlist not found")
    @DeleteMapping("/{customerId}/{productId}")
    ResponseEntity<WishlistResponse> removeProduct(
            @PathVariable String customerId,
            @PathVariable String productId
    );

    @Operation(
            summary = "Get customer's wishlist",
            description = "Returns the full wishlist for a given customer."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Wishlist found",
            content = @Content(schema = @Schema(implementation = WishlistResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Wishlist not found")
    @GetMapping("/{customerId}")
    ResponseEntity<WishlistResponse> listProducts(
            @PathVariable String customerId
    );

    @Operation(
            summary = "Check if product is in wishlist",
            description = "Checks whether a given product is present in customer's wishlist."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Check performed successfully",
            content = @Content(schema = @Schema(implementation = WishlistContainsResponse.class))
    )
    @ApiResponse(responseCode = "404", description = "Wishlist not found")
    @GetMapping("/{customerId}/contains/{productId}")
    ResponseEntity<WishlistContainsResponse> containsProduct(
            @PathVariable String customerId,
            @PathVariable String productId
    );
}