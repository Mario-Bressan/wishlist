package com.mpb.wishlist.infra.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataWishlistRepository extends MongoRepository<WishlistDocument, String> {

    Optional<WishlistDocument> findByCustomerId(String customerId);
}
