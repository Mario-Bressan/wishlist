package com.mpb.wishlist.api;

import com.mpb.wishlist.api.dto.WishlistContainsResponse;
import com.mpb.wishlist.api.dto.WishlistItemRequest;
import com.mpb.wishlist.api.dto.WishlistResponse;
import com.mpb.wishlist.domain.model.Wishlist;
import com.mpb.wishlist.domain.usecase.WishlistItemInput;

public final class WishlistApiMapper {

    private WishlistApiMapper() {
    }

    public static WishlistItemInput toInput(String customerId, WishlistItemRequest request) {
        return new WishlistItemInput(customerId, request.productId());
    }

    public static WishlistItemInput toInput(String customerId, String productId) {
        return new WishlistItemInput(customerId, productId);
    }

    public static WishlistResponse toResponse(Wishlist wishlist) {
        return new WishlistResponse(wishlist.getCustomerId(), wishlist.getProductIds());
    }

    public static WishlistContainsResponse toContainsResponse(
            String customerId,
            String productId,
            boolean contains) {
        return new WishlistContainsResponse(customerId, productId, contains);
    }
}
