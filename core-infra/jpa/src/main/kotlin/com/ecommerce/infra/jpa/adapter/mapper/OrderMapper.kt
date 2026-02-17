package com.ecommerce.infra.jpa.adapter.mapper

import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import com.ecommerce.infra.jpa.entity.OrderEntity
import com.ecommerce.infra.jpa.entity.OrderItemEntity

object OrderMapper {

    fun toEntity(order: Order): OrderEntity {
        return OrderEntity(
            accountId = order.accountId.value,
            orderKey = order.orderKey.value,
            name = order.name.value,
            totalPrice = order.price.value,
            status = order.status,
        )
    }

    fun toDomain(orderEntity: OrderEntity): Order {
        return Order.reconstruct(
            id = orderEntity.id,
            orderKey = orderEntity.orderKey,
            accountId = orderEntity.accountId,
            name = orderEntity.name,
            price = orderEntity.totalPrice,
            status = orderEntity.status,
        )
    }

    fun toEntity(orderItems: List<OrderItem>): List<OrderItemEntity> {
        return orderItems.map { toEntity(it) }
    }

    fun toEntity(orderItem: OrderItem): OrderItemEntity {
        return OrderItemEntity(
            orderId = orderItem.orderId.value,
            productId = orderItem.productId.value,
            quantity = orderItem.quantity.value,
            unitPrice = orderItem.price.unitPrice,
            totalPrice = orderItem.price.totalPrice,
        )
    }

    fun toDomain(orderItemEntity: OrderItemEntity): OrderItem {
        return OrderItem.reconstruct(
            id = orderItemEntity.id,
            orderId = orderItemEntity.orderId,
            productId = orderItemEntity.productId,
            quantity = orderItemEntity.quantity,
            unitPrice = orderItemEntity.unitPrice,
            totalPrice = orderItemEntity.totalPrice,
        )
    }
}
