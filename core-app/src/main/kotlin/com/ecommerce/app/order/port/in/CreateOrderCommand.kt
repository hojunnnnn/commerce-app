package com.ecommerce.app.order.port.`in`

import com.ecommerce.domain.order.NewOrder

data class CreateOrderCommand(
    val newOrder: NewOrder
)