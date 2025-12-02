package com.ecommerce.infra.jpa.repository

import com.ecommerce.infra.jpa.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository: JpaRepository<ProductEntity, Long> {
}