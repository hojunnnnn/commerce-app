package com.ecommerce.domain.order.vo

@JvmInline
value class OrderId(val value: Long) {

    init {
        if (value < 0) {

        }
    }

    companion object {
        fun generate(value: Long = 0L): OrderId = OrderId(value)
    }

}