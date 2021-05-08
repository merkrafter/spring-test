package com.merkrafter.estimation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

/**
 * @author merkrafter
 */
@DataJpaTest
internal class ProductRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val productRepository: ProductRepository
) {

    @Test
    fun `should find product with a price in the given range`() {
        val price = 3500
        val book = createAndStoreProduct("Book", price)
        val found = productRepository.findAllByPriceInEuroCentsBetween(price - 1, price + 1)
        assertThat(found).contains(book)
    }

    @Test
    fun `should find product with a price equal to the lower limit of the range`() {
        val price = 5000
        val book = createAndStoreProduct("Book", price)
        val found = productRepository.findAllByPriceInEuroCentsBetween(price, price + 1)
        assertThat(found).contains(book)
    }

    @Test
    fun `should find product with a price equal to the upper limit of the range`() {
        val price = 790
        val magazine = createAndStoreProduct("Magazine", price)
        val found = productRepository.findAllByPriceInEuroCentsBetween(price - 1, price)
        assertThat(found).contains(magazine)
    }

    @Test
    fun `should not find product with a price outside the range`() {
        val price = 5000
        createAndStoreProduct("Book", price)
        val found = productRepository.findAllByPriceInEuroCentsBetween(price + 1, price + 4)
        assertThat(found).isEmpty()
    }

    @Test
    fun `should not find product when search range is empty`() {
        val price = 5000
        createAndStoreProduct("Book", price)
        val found = productRepository.findAllByPriceInEuroCentsBetween(price + 1, price - 1)
        assertThat(found).isEmpty()
    }

    private fun createAndStoreProduct(name: String, price: Int): Product {
        val product = Product(name, price)
        entityManager.persist(product)
        entityManager.flush()
        return product
    }
}