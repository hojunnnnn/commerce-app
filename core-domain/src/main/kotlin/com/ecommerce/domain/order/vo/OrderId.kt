package com.ecommerce.domain.order.vo

import com.ecommerce.domain.order.exception.OrderIdNegativeException

@JvmInline
value class OrderId(val value: Long) {

    init {
        if (value < 0) {
            throw OrderIdNegativeException()
        }
    }

    companion object {
        fun generate(value: Long = 0L): OrderId = OrderId(value)
    }

}