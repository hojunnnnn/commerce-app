package com.ecommerce.domain.order.vo

import com.ecommerce.domain.order.exception.OrderInvalidPriceException
import java.math.BigDecimal

data class OrderItemPrice(
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal,
) {

    init {
        if (unitPrice <= BigDecimal.ZERO) {
            throw OrderInvalidPriceException()
        }
        if (totalPrice <= BigDecimal.ZERO) {
            throw OrderInvalidPriceException()
        }
    }
}