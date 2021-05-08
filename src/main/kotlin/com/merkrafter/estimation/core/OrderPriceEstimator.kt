package com.merkrafter.estimation.core

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

/**
 * @author merkrafter
 */
class OrderPriceEstimator(private val basePrice: Price = Price.neutral()) {
    fun estimatePriceOf(order: Order): Result<Price, String> {
        return if (order.isEmpty()) {
            Ok(Price.neutral())
        } else {
            val subSums = computeSubSumsOf(order)
            computeTotal(subSums).tryAdd(Ok(basePrice))
        }
    }

    private fun computeTotal(subSums: List<Result<Price, String>>): Result<Price, String> {
        return subSums.reduce { acc, nextSubSum ->
            acc.tryAdd(nextSubSum)
        }
    }

    private fun Result<Price, String>.tryAdd(other: Result<Price, String>): Result<Price, String> {
        return if (this is Ok && other is Ok) {
            this.value.plus(other.value)
        } else {
            Err("there was an error")
        }
    }

    private fun computeSubSumsOf(order: Order): List<Result<Price, String>> {
        return order.items.map { it.item.price.times(it.amount) }
    }

}

private fun Order.isEmpty(): Boolean {
    return items.isEmpty()
}