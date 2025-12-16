package com.ecommerce.domain.order.vo

import java.math.BigDecimal

data class OrderItemPrice(
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal,
) {
}