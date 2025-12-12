package com.mpb.wishlist.infra.persistence.mongo;

import com.mpb.wishlist.domain.model.Wishlist;
import com.mpb.wishlist.domain.port.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishlistMongoRepository implements WishlistRepository {

    private final SpringDataWishlistRepository repository;

    @Override
    public Optional<Wishlist> findByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId)
                .map(WishlistDocumentMapper::toDomain);
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        var document = repository.save(WishlistDocumentMapper.toDocument(wishlist));
        return WishlistDocumentMapper.toDomain(document);
    }
}
