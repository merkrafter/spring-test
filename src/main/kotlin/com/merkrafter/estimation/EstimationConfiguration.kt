package com.merkrafter.estimation

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author merkrafter
 */
@Configuration
class EstimationConfiguration {
    @Bean
    fun databaseInitializer(productRepository: ProductRepository) = ApplicationRunner {
        productRepository.saveAll(
            listOf(
                Product("1m pipes", 5000),
                Product("1m cables", 3000),
                Product("10kg sand", 2500)
            )
        )
    }
}