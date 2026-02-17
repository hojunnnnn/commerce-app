package com.ecommerce.app.order.port.out

import com.ecommerce.domain.order.OrderItem

interface OrderItemRepository {

    fun findAllByOrderId(orderId: Long): List<OrderItem>

    fun saveAll(items: List<OrderItem>): List<OrderItem>
}