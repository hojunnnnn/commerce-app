package com.ecommerce.infra.jpa.entity

import com.ecommerce.domain.product.vo.ProductStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val costPrice: BigDecimal,

    @Column(nullable = false)
    val salesPrice: BigDecimal,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: ProductStatus,
) : BaseEntity()