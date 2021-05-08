package com.merkrafter.estimation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author merkrafter
 */
@WebMvcTest
internal class EstimationControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var productRepository: ProductRepository

    private val productsPath = "/products"

    @Test
    fun `GETing 'products' should return all products`() {
        val book = Product("Book", 2399)
        val cd = Product("CD", 1499)
        every { productRepository.findAll() } returns listOf(book, cd)
        mockMvc.perform(get(productsPath).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.[0]").value(book.name))
            .andExpect(jsonPath("\$.[1]").value(cd.name))
    }

    @Test
    fun `GETing 'products' with empty repository should have status code OK`() {
        every { productRepository.findAll() } returns emptyList()
        mockMvc.perform(get(productsPath).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }
}