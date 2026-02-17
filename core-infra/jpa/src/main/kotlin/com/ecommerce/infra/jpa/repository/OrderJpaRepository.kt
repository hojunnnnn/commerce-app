package com.ecommerce.infra.jpa.repository

import com.ecommerce.infra.jpa.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository: JpaRepository<OrderEntity, Long> {


}