package com.mpb.wishlist.domain.model

import com.mpb.wishlist.exception.LimitExceededException
import spock.lang.Specification

class WishlistSpec extends Specification {

    private static final String CUSTOMER_ID = "customer-1"
    private static final String PRODUCT_ID = "product-1"

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
            def anotherProduct = "product-2"
        when:
            def removed = wishlist.removeProduct(anotherProduct)
        then:
            !removed
            wishlist.productIds == Arrays.asList(PRODUCT_ID)
    }

}
