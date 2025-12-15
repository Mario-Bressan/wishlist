package com.mpb.wishlist.domain.model;

import com.mpb.wishlist.exception.LimitExceededException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.mpb.wishlist.domain.validation.WishlistValidator.requireCustomerId;
import static com.mpb.wishlist.domain.validation.WishlistValidator.requireProductId;

@Getter
@EqualsAndHashCode(of = "customerId")
public class Wishlist {

    private static final int MAX_PRODUCTS = 20;

    private final String customerId;
    private final List<String> productIds = new ArrayList<>();

    public Wishlist(String customerId) {
        this.customerId = requireCustomerId(customerId);
    }

    public boolean addProduct(String productId) {
        productId = requireProductId(productId);

        if (productIds.contains(productId)) {
            return false;
        }

        if (productIds.size() >= MAX_PRODUCTS) {
            throw new LimitExceededException(this.customerId, MAX_PRODUCTS);
        }

        return productIds.add(productId);
    }

    public boolean removeProduct(String productId) {
        productId = requireProductId(productId);
        return productIds.remove(productId);
    }

    public boolean containsProduct(String productId) {
        productId = requireProductId(productId);
        return productIds.contains(productId);
    }

    public List<String> getProductIds() {
        return List.copyOf(productIds);
    }
}
