package com.mpb.wishlist.config;

import com.mpb.wishlist.domain.port.WishlistRepository;
import com.mpb.wishlist.domain.usecase.WishlistUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public WishlistUseCase wishlistUseCase(WishlistRepository wishlistRepository) {
        return new WishlistUseCase(wishlistRepository);
    }
}
