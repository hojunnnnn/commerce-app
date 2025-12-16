package com.ecommerce.infra.jpa.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal


@Table
@Entity
class OrderItemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val orderId: Long,

    val productId: Long,

    val quantity: Int,

    val unitPrice: BigDecimal,

    val totalPrice: BigDecimal,
): BaseEntity()