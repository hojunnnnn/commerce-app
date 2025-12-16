package com.ecommerce.domain.order.vo

@JvmInline
value class OrderItemId(val value: Long) {

    init {
        if (value < 0) {

        }
    }

    companion object {
        fun generate(value: Long): OrderItemId = OrderItemId(value)
    }
}