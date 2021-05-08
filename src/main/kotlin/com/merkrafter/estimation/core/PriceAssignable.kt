package com.merkrafter.estimation.core

/**
 * @author merkrafter
 */
interface PriceAssignable {
    val price: Price
}

@JvmInline
value class Price(val value: Int)
