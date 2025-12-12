package com.mpb.wishlist.infra.persistence.mongo;

import com.mpb.wishlist.domain.model.Wishlist;

import java.util.ArrayList;

public final class WishlistDocumentMapper {

    private WishlistDocumentMapper() {
    }

    public static WishlistDocument toDocument(Wishlist wishlist) {
        if (wishlist == null) {
            return null;
        }

        return WishlistDocument.builder()
                .customerId(wishlist.getCustomerId())
                .productIds(new ArrayList<>(wishlist.getProductIds()))
                .build();
    }

    public static Wishlist toDomain(WishlistDocument document) {
        if (document == null) {
            return null;
        }

        Wishlist wishlist = new Wishlist(document.getCustomerId());
        if (document.getProductIds() != null) {
            document.getProductIds().forEach(wishlist::addProduct);
        }
        return wishlist;
    }
}
