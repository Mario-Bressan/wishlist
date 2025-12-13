package com.mpb.wishlist.api.dto;

public record WishlistContainsResponse(String customerId, String productId, boolean contains) {}
