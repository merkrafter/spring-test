package com.merkrafter.estimation.core

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class Price private constructor(val value: Int) {

    override fun equals(other: Any?): Boolean {
        return if (other is Price) {
            value == other.value
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return value
    }

    /**
     * Returns a new price and leaves this instance unmodified.
     */
    fun times(multiplier: UInt): Result<Price, String> {
        return Ok(Price(value * multiplier.toInt()))
    }

    fun plus(price: Price): Result<Price, String> {
        return Ok(Price(value + price.value))
    }

    companion object {
        fun fromInt(value: Int): Price {
            return Price(value)
        }

        fun neutral(): Price {
            return Price(0)
        }
    }
}