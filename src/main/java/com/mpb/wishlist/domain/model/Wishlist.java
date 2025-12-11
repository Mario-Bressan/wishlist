package com.mpb.wishlist.domain.model;

import com.mpb.wishlist.exception.LimitExceededException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "customerId")
public class Wishlist {

    private static final int MAX_ITEMS = 20;

    private final String customerId;

    private final List<String> productIds = new ArrayList<>();

    public Wishlist(String customerId) {
        this.customerId = customerId;
    }

    public boolean addProduct(String productId) {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("productId cannot be null or empty");
        }

        if (productIds.contains(productId)) {
            return false;
        }

        if (productIds.size() >= MAX_ITEMS) {
            throw new LimitExceededException(this.customerId, MAX_ITEMS);
        }

        return productIds.add(productId);
    }

    public boolean removeProduct(String productId) {
        return productIds.remove(productId);
    }

    public List<String> getProductIds() {
        return List.copyOf(productIds);
    }

}
