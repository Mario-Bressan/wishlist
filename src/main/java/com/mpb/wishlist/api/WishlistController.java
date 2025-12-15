package com.mpb.wishlist.api;

import com.mpb.wishlist.api.dto.WishlistContainsResponse;
import com.mpb.wishlist.api.dto.WishlistItemRequest;
import com.mpb.wishlist.api.dto.WishlistResponse;
import com.mpb.wishlist.domain.model.Wishlist;
import com.mpb.wishlist.domain.usecase.WishlistUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.mpb.wishlist.api.WishlistApiMapper.toContainsResponse;
import static com.mpb.wishlist.api.WishlistApiMapper.toInput;
import static com.mpb.wishlist.api.WishlistApiMapper.toResponse;

@RestController
@RequiredArgsConstructor
public class WishlistController implements WishlistApi {

    private final WishlistUseCase useCase;

    @Override
    public ResponseEntity<WishlistResponse> addProduct(String customerId, WishlistItemRequest request) {
        Wishlist wishlist = useCase.addProduct(toInput(customerId, request));
        return ResponseEntity.ok(toResponse(wishlist));
    }

    @Override
    public ResponseEntity<WishlistResponse> removeProduct(String customerId, String productId) {
        Wishlist wishlist = useCase.removeProduct(toInput(customerId, productId));
        return ResponseEntity.ok(toResponse(wishlist));
    }

    @Override
    public ResponseEntity<WishlistResponse> listProducts(String customerId) {
        Wishlist wishlist = useCase.getWishlist(customerId);
        return ResponseEntity.ok(toResponse(wishlist));
    }

    @Override
    public ResponseEntity<WishlistContainsResponse> containsProduct(String customerId, String productId) {
        Wishlist wishlist = useCase.getWishlist(customerId);
        return ResponseEntity.ok(toContainsResponse(customerId, productId, wishlist.containsProduct(productId)));
    }
}
