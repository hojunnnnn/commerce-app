package com.ecommerce.domain.order.vo

import com.ecommerce.domain.order.exception.OrderIdNegativeException

@JvmInline
value class OrderItemId(val value: Long) {

    init {
        if (value < 0) {
            throw OrderIdNegativeException()
        }
    }

    companion object {
        fun generate(value: Long): OrderItemId = OrderItemId(value)
    }
}