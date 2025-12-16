package com.ecommerce.infra.jpa.entity

import com.ecommerce.domain.order.vo.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal


@Table
@Entity
class OrderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val orderKey: String,

    val name: String,

    val totalPrice: BigDecimal,

    val status: OrderStatus,
): BaseEntity()