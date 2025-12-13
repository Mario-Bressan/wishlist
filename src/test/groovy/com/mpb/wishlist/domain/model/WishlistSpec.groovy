package com.mpb.wishlist.domain.model

import com.mpb.wishlist.exception.LimitExceededException
import spock.lang.Specification

import static com.mpb.wishlist.support.WishlistTestConstants.CUSTOMER_ID
import static com.mpb.wishlist.support.WishlistTestConstants.OTHER_PRODUCT_ID
import static com.mpb.wishlist.support.WishlistTestConstants.PRODUCT_ID

class WishlistSpec extends Specification {

    def "should add product when wishlist is not full"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
        when:
            def result = wishlist.addProduct(PRODUCT_ID)
        then:
            result
            wishlist.productIds.contains(PRODUCT_ID)
    }

    def "should ignore duplicate product and return false" () {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)
            def before = new ArrayList<>(wishlist.productIds)
        when:
            def result = wishlist.addProduct(PRODUCT_ID)
        then:
            !result
            wishlist.productIds == before
    }

    def "should throw IllegalArgumentException when productId is null or empty" () {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
        when:
            wishlist.addProduct(invalidProductId)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.message == "productId cannot be null or empty"
        where:
            invalidProductId << [null, ""]
    }

    def "should throw LimitExceededException when adding beyond max items" () {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            for (int i = 1; i <= 20; i++) {
                wishlist.addProduct("product-"+i)
            }
        when:
            wishlist.addProduct("product-21")
        then:
            thrown(LimitExceededException)
    }

    def "should remove existing product and return true"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)
        when:
            def removed = wishlist.removeProduct(PRODUCT_ID)
        then:
            removed
            wishlist.productIds.isEmpty()
    }

    def "should return false when trying to remove non existing product"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)
        when:
            def removed = wishlist.removeProduct(OTHER_PRODUCT_ID)
        then:
            !removed
            wishlist.productIds == Arrays.asList(PRODUCT_ID)
    }

    def "should return true when wishlist contains product"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)

        expect:
            wishlist.containsProduct(PRODUCT_ID)
    }

    def "should return false when wishlist does not contain product"() {
        given:
            def wishlist = new Wishlist(CUSTOMER_ID)
            wishlist.addProduct(PRODUCT_ID)

        expect:
            !wishlist.containsProduct(OTHER_PRODUCT_ID)
    }

}
