package com.merkrafter.estimation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author merkrafter
 */
@RestController
class EstimationController {

    @GetMapping("/")
    fun listProducts(): List<String> {
        return listOf("abc", "def")
    }
}