package com.ecommerce.infra.jpa.repository

import com.ecommerce.infra.jpa.entity.OrderItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemJpaRepository: JpaRepository<OrderItemEntity, Long> {

    fun findAllByOrderId(orderId: Long): List<OrderItemEntity>
}