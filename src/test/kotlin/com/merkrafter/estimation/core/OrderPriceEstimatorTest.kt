package com.merkrafter.estimation.core

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * @author merkrafter
 */
internal class OrderPriceEstimatorTest {
    @Test
    fun `should have neutral price as default base price`() {
        val estimator = OrderPriceEstimator()
        val rawPrice = 1
        val price = Price.fromInt(rawPrice)
        val order = orderWithPrices(rawPrice)
        val estimatedTotal = estimator.estimatePriceOf(order)
        assertEquals(price, estimatedTotal.component1())
    }

    @ParameterizedTest
    @ValueSource(ints = [-3, 0, 1, Byte.MAX_VALUE.toInt(), Int.MAX_VALUE])
    fun `should return neutral price on empty order`(price: Int) {
        val estimator = OrderPriceEstimator(basePrice = Price.fromInt(price))
        val estimatedTotal = estimator.estimatePriceOf(emptyOrder())
        assertEquals(Price.neutral(), estimatedTotal.component1())
    }

    @Test
    fun `should consider order item amount`() {
        val price = Price.fromInt(2)
        val amount = 3u
        val order = Order(listOf(price x amount))
        val expectedTotal = Price.fromInt(6)
        val estimator = OrderPriceEstimator()
        val estimatedTotal = estimator.estimatePriceOf(order)
        assertEquals(expectedTotal, estimatedTotal.component1())
    }

    @Test
    fun `should add prices`() {
        val order = orderWithPrices(1, 2, 3)
        val expectedTotal = Price.fromInt(6)
        val estimator = OrderPriceEstimator()
        val estimatedTotal = estimator.estimatePriceOf(order)
        assertEquals(expectedTotal, estimatedTotal.component1())
    }

    @Test
    fun `should compute the correct total of all products, amounts, and the base price`() {
        val order = Order(
            listOf(
                Price.fromInt(1) x 5u,
                Price.fromInt(3) x 3u,
            )
        )
        val estimator = OrderPriceEstimator(basePrice = Price.fromInt(2))
        val expectedTotal = Price.fromInt(16)
        val estimatedTotal = estimator.estimatePriceOf(order)
        assertEquals(expectedTotal, estimatedTotal.component1())
    }

    private fun emptyOrder(): Order {
        return Order(emptyList())
    }

    private fun orderWithPrices(vararg price: Int): Order {
        return Order(items = price.map { orderItemWithPrice(Price.fromInt(it)) })
    }

    /**
     * @return an OrderItem with anonymous PriceAssignable and amount 1
     */
    private fun orderItemWithPrice(price: Price): OrderItem {
        return price x 1u
    }

    private infix fun Price.x(amount: UInt): OrderItem {
        return OrderItem(this.toPriceAssignable(), amount)
    }

    private fun Price.toPriceAssignable(): PriceAssignable {
        val assignable = mockk<PriceAssignable>()
        every { assignable.price } returns this
        return assignable
    }
}