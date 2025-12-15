package com.mpb.wishlist.api;

import com.mpb.wishlist.api.dto.WishlistContainsResponse;
import com.mpb.wishlist.api.dto.WishlistItemRequest;
import com.mpb.wishlist.api.dto.WishlistResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Wishlist", description = "Operations for managing a customer's wishlist")
@RequestMapping("/wishlist")
@Validated
public interface WishlistApi {

    @PostMapping("/{customerId}")
    ResponseEntity<WishlistResponse> addProduct(
            @PathVariable
            @NotBlank(message = "customerId is required")
            @Size(max = 64, message = "customerId must be at most 64 chars")
            String customerId,
            @Valid @RequestBody WishlistItemRequest request
    );

    @DeleteMapping("/{customerId}/{productId}")
    ResponseEntity<WishlistResponse> removeProduct(
            @PathVariable
            @NotBlank(message = "customerId is required")
            @Size(max = 64, message = "customerId must be at most 64 chars")
            String customerId,

            @PathVariable
            @NotBlank(message = "productId is required")
            @Size(max = 64, message = "productId must be at most 64 chars")
            String productId
    );

    @GetMapping("/{customerId}")
    ResponseEntity<WishlistResponse> listProducts(
            @PathVariable
            @NotBlank(message = "customerId is required")
            @Size(max = 64, message = "customerId must be at most 64 chars")
            String customerId
    );

    @GetMapping("/{customerId}/contains/{productId}")
    ResponseEntity<WishlistContainsResponse> containsProduct(
            @PathVariable
            @NotBlank(message = "customerId is required")
            @Size(max = 64, message = "customerId must be at most 64 chars")
            String customerId,

            @PathVariable
            @NotBlank(message = "productId is required")
            @Size(max = 64, message = "productId must be at most 64 chars")
            String productId
    );
}