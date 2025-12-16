package com.ecommerce.domain.order.vo

import com.ecommerce.domain.order.exception.OrderInvalidQuantityException

@JvmInline
value class OrderQuantity(val value: Int) {

    init {
        if(value <= 0) {
            throw OrderInvalidQuantityException()
        }
    }
}