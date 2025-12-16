package com.ecommerce.domain.order.vo

import com.ecommerce.domain.order.exception.OrderInvalidPriceException
import java.math.BigDecimal

@JvmInline
value class OrderPrice(val value: BigDecimal) {

    init {
        if(value <= BigDecimal.ZERO) {
            throw OrderInvalidPriceException()
        }
    }
}