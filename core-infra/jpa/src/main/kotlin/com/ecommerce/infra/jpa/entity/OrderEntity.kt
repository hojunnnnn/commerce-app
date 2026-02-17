package com.ecommerce.infra.jpa.entity

import com.ecommerce.domain.order.vo.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(
    indexes = [
        Index(name = "udx_order_key", columnList = "orderKey", unique = true),
    ],
)
class OrderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val accountId: Long,

    @Column(nullable = false)
    val orderKey: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val totalPrice: BigDecimal,

    @Column(nullable = false)
    val status: OrderStatus,
): BaseEntity()