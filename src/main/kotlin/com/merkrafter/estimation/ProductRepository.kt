package com.merkrafter.estimation

import org.springframework.data.repository.CrudRepository

/**
 * @author merkrafter
 */
interface ProductRepository : CrudRepository<Product, Long> {
    fun findAllByPriceInEuroCentsBetween(lower: Int, upper: Int): Iterable<Product>
}