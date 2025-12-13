package com.mpb.wishlist.api.dto;

import java.util.List;

public record WishlistResponse(String customuerId, List<String> productIds) {}
