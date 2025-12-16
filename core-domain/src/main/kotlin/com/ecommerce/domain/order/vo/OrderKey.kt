package com.ecommerce.domain.order.vo

import com.ecommerce.domain.order.exception.OrderKeyBlankException

@JvmInline
value class OrderKey(val value: String) {

    init {
        if (value.isBlank()) {
            throw OrderKeyBlankException()
        }
    }
}