package com.ecommerce.domain.order.vo

import com.ecommerce.domain.order.exception.OrderNameBlankException

@JvmInline
value class OrderName(val value: String) {

    init {
        if (value.isBlank()) {
            throw OrderNameBlankException()
        }
    }
}