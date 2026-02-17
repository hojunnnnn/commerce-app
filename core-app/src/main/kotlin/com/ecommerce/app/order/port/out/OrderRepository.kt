package com.ecommerce.app.order.port.out

import com.ecommerce.domain.order.Order

interface OrderRepository {

    fun findById(id: Long): Order?

    fun findAll(): List<Order>

    fun save(order: Order): Order
}