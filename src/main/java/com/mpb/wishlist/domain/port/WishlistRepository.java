package com.mpb.wishlist.domain.port;

import com.mpb.wishlist.domain.model.Wishlist;

import java.util.Optional;

public interface WishlistRepository {

    Optional<Wishlist> findByCustomerId(String customerId);

    Wishlist save(Wishlist wishlist);
}
