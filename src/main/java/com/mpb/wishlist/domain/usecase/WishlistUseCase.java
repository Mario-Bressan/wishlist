package com.mpb.wishlist.domain.usecase;

import com.mpb.wishlist.domain.model.Wishlist;
import com.mpb.wishlist.domain.port.WishlistRepository;
import com.mpb.wishlist.exception.WishlistNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WishlistUseCase {

    private final WishlistRepository wishlistRepository;

    public Wishlist addProduct(WishlistItemInput input) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(input.customerId())
                .orElseGet(() -> new Wishlist(input.customerId()));

        wishlist.addProduct(input.productId());

        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeProduct(WishlistItemInput input) {
        Wishlist wishlist = loadOrThrow(input.customerId());

        boolean removed = wishlist.removeProduct(input.productId());

        if (removed) {
            return wishlistRepository.save(wishlist);
        }

        return wishlist;
    }

    public List<String> listProducts(String customerId) {
        Wishlist wishlist = loadOrThrow(customerId);
        return wishlist.getProductIds();
    }

    public boolean containsProduct(WishlistItemInput input) {
        Wishlist wishlist = loadOrThrow(input.customerId());
        return wishlist.getProductIds().contains(input.productId());
    }

    private Wishlist loadOrThrow(String customerId) {
        return wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));
    }
}
