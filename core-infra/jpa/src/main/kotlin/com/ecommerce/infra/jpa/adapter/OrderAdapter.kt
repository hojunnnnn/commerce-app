package com.ecommerce.infra.jpa.adapter

import com.ecommerce.app.order.port.out.OrderItemRepository
import com.ecommerce.app.order.port.out.OrderRepository
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import com.ecommerce.infra.jpa.adapter.mapper.OrderMapper
import com.ecommerce.infra.jpa.repository.OrderItemJpaRepository
import com.ecommerce.infra.jpa.repository.OrderJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component


@Component
class OrderAdapter(
    private val orderJpaRepository: OrderJpaRepository,
    private val orderItemJpaRepository: OrderItemJpaRepository,
): OrderRepository, OrderItemRepository {

    override fun findById(id: Long): Order? {
        return orderJpaRepository.findByIdOrNull(id)?.let { OrderMapper.toDomain(it) }
    }

    override fun findAll(): List<Order> {
        return orderJpaRepository.findAll().map { OrderMapper.toDomain(it) }
    }

    override fun findAllByOrderId(orderId: Long): List<OrderItem> {
        return orderItemJpaRepository.findAllByOrderId(orderId).map { OrderMapper.toDomain(it) }
    }

    override fun save(order: Order): Order {
        val entity = OrderMapper.toEntity(order)
        val savedEntity = orderJpaRepository.save(entity)
        return OrderMapper.toDomain(savedEntity)
    }

    override fun saveAll(items: List<OrderItem>): List<OrderItem> {
        if (items.isEmpty()) {
            return emptyList()
        }
        val entities = OrderMapper.toEntity(items)
        val savedEntities = orderItemJpaRepository.saveAll(entities)
        return savedEntities.map { OrderMapper.toDomain(it) }
    }
}
