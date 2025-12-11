package com.mpb.wishlist.domain.usecase

import com.mpb.wishlist.domain.model.Wishlist
import com.mpb.wishlist.domain.port.WishlistRepository
import com.mpb.wishlist.exception.WishlistNotFoundException
import spock.lang.Specification
import spock.lang.Subject

class WishlistUseCaseSpec extends Specification {

    private static final String CUSTOMER_ID = "customer-1"
    private static final String PRODUCT_ID = "product-1"
    private static final String OTHER_PRODUCT_ID = "product-2"

    WishlistRepository wishlistRepository = Mock()

    @Subject
    WishlistUseCase useCase = new WishlistUseCase(wishlistRepository)

    def "should add product to existing wishlist"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)

        when:
            def result = useCase.addProduct(new WishlistItemInput(CUSTOMER_ID, PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.of(wishlist)
            1 * wishlistRepository.save(_ as Wishlist) >> { Wishlist w -> w }

            result.customerId == CUSTOMER_ID
            result.productIds.contains(PRODUCT_ID)
    }

    def "should create new wishlist when adding product for customer without wishlist"() {
        when:
            def result = useCase.addProduct(new WishlistItemInput(CUSTOMER_ID, PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.empty()
            1 * wishlistRepository.save(_ as Wishlist) >> { Wishlist w -> w }

            result.customerId == CUSTOMER_ID
            result.productIds == [PRODUCT_ID]
    }

    def "should remove product and save when product exists in wishlist"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)

        when:
            def result = useCase.removeProduct(new WishlistItemInput(CUSTOMER_ID, PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.of(wishlist)
            1 * wishlistRepository.save(_ as Wishlist) >> { Wishlist w -> w }

            !result.productIds.contains(PRODUCT_ID)
    }

    def "should not save when trying to remove product that is not in wishlist"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)

        when:
            def result = useCase.removeProduct(new WishlistItemInput(CUSTOMER_ID, OTHER_PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.of(wishlist)
            0 * wishlistRepository.save(_)

            result.productIds == wishlist.productIds
    }

    def "should throw WishlistNotFoundException when removing product from non existing wishlist"() {
        when:
            useCase.removeProduct(new WishlistItemInput(CUSTOMER_ID, PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.empty()
            0 * wishlistRepository.save(_)

            thrown(WishlistNotFoundException)
    }

    def "should list products for existing wishlist"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)
            wishlist.addProduct(OTHER_PRODUCT_ID)

        when:
            def result = useCase.listProducts(CUSTOMER_ID)

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.of(wishlist)

            result.size() == 2
    }

    def "should throw WishlistNotFoundException when listing products for non existing wishlist"() {
        when:
            useCase.listProducts(CUSTOMER_ID)

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.empty()

            thrown(WishlistNotFoundException)
    }

    def "should return true when wishlist contains product"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)

        when:
            def result = useCase.containsProduct(new WishlistItemInput(CUSTOMER_ID, PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.of(wishlist)

            result
    }

    def "should return false when wishlist does not contain product"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(OTHER_PRODUCT_ID)

        when:
            def result = useCase.containsProduct(new WishlistItemInput(CUSTOMER_ID, PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.of(wishlist)

            !result
    }

    def "should throw WishlistNotFoundException when checking containsProduct for non existing wishlist"() {
        when:
            useCase.containsProduct(new WishlistItemInput(CUSTOMER_ID, PRODUCT_ID))

        then:
            1 * wishlistRepository.findByCustomerId(CUSTOMER_ID) >> Optional.empty()

            thrown(WishlistNotFoundException)
    }

}
