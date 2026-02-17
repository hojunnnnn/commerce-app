package com.ecommerce.infra.jpa.repository

import com.ecommerce.domain.product.vo.ProductStatus
import com.ecommerce.infra.jpa.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository: JpaRepository<ProductEntity, Long> {

    fun findByIdInAndStatus(ids: Collection<Long>, status: ProductStatus): List<ProductEntity>
}