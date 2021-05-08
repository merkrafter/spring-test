package com.merkrafter.estimation.core

/**
 * @author merkrafter
 */
data class Order(val items: List<OrderItem>)

/**
 * @author merkrafter
 */
data class OrderItem @OptIn(ExperimentalUnsignedTypes::class) constructor(val item: PriceAssignable, val amount: UInt)