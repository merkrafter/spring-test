package com.merkrafter.estimation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author merkrafter
 */
@RestController
class EstimationController(val productRepository: ProductRepository) {

    @GetMapping("/products")
    fun listProductNames(): List<String> {
        return productRepository.findAll().map { it.name }
    }
}