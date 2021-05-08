package com.merkrafter.estimation.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author merkrafter
 */
internal class PriceTest {

    @Test
    fun `neutral price should be equal to 0`() {
        assertEquals(Price.fromInt(0), Price.neutral())
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 5, 42, Int.MAX_VALUE])
    fun `price plus neutral should be original`(price: Int) {
        val originalPrice = Price.fromInt(price)
        val pricePlusNeutral = originalPrice.plus(Price.neutral())
        assertEquals(originalPrice, pricePlusNeutral.component1())
    }

    @Test
    fun `should return the correct price when added`() {
        val twoEuros = Price.fromInt(2)
        val fourEuros = Price.fromInt(4)
        assertEquals(fourEuros, twoEuros.plus(twoEuros).component1())
    }

    @Test
    fun `plus() should leave the original object unchanged`() {
        val somePrice = Price.fromInt(3)  // arbitrary, non-zero
        val neutralPrice = Price.neutral()

        neutralPrice.plus(somePrice)

        assertEquals(Price.neutral(), neutralPrice)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 5, 42, Int.MAX_VALUE])
    fun `price times 0 should be neutral`(price: Int) {
        val priceTimesZero = Price.fromInt(price).times(0u)
        assertEquals(Price.neutral(), priceTimesZero.component1())
    }

    @Test
    fun `should return the correct price when multiplied`() {
        val twoEuros = Price.fromInt(2)
        val fourEuros = Price.fromInt(4)
        assertEquals(fourEuros, twoEuros.times(2u).component1())
    }

    @Test
    fun `times() should leave the original object unchanged`() {
        val priceValue = 3  // arbitrary, non-zero
        val somePrice = Price.fromInt(priceValue)
        val copyOfSomePrice = Price.fromInt(priceValue)

        somePrice.times(0u)

        assertEquals(copyOfSomePrice, somePrice)
    }
}