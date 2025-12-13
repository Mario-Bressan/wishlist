package com.mpb.wishlist.api;

import com.mpb.wishlist.api.dto.WishlistContainsResponse;
import com.mpb.wishlist.api.dto.WishlistItemRequest;
import com.mpb.wishlist.api.dto.WishlistResponse;
import com.mpb.wishlist.domain.model.Wishlist;
import com.mpb.wishlist.domain.usecase.WishlistUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mpb.wishlist.api.WishlistApiMapper.toContainsResponse;
import static com.mpb.wishlist.api.WishlistApiMapper.toInput;
import static com.mpb.wishlist.api.WishlistApiMapper.toResponse;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistUseCase useCase;

    @PostMapping("/{customerId}")
    public ResponseEntity<WishlistResponse> addProduct(@PathVariable String customerId,
                                                       @Valid @RequestBody WishlistItemRequest request) {
        Wishlist wishlist = useCase.addProduct(toInput(customerId, request));
        return ResponseEntity.ok(toResponse(wishlist));
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<WishlistResponse> removeProduct(@PathVariable String customerId,
                                                          @PathVariable String productId) {
        Wishlist wishlist = useCase.removeProduct(toInput(customerId, productId));
        return ResponseEntity.ok(toResponse(wishlist));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<WishlistResponse> listProducts(@PathVariable String customerId) {
        Wishlist wishlist = useCase.getWishlist(customerId);
        return ResponseEntity.ok(toResponse(wishlist));
    }

    @GetMapping("/{customerId}/contains/{productId}")
    public ResponseEntity<WishlistContainsResponse> containsProduct(
            @PathVariable String customerId,
            @PathVariable String productId) {
        Wishlist wishlist = useCase.getWishlist(customerId);
        return ResponseEntity.ok(toContainsResponse(customerId, productId, wishlist.containsProduct(productId)));
    }
}
