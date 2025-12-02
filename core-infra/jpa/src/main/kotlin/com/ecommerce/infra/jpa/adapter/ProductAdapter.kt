package com.ecommerce.infra.jpa.adapter

import com.ecommerce.app.product.port.out.ProductRepository
import com.ecommerce.domain.product.Product
import com.ecommerce.infra.jpa.adapter.mapper.ProductMapper
import com.ecommerce.infra.jpa.repository.ProductJpaRepository
import org.springframework.stereotype.Component

@Component
class ProductAdapter(
    private val productJpaRepository: ProductJpaRepository,
): ProductRepository {

    override fun save(product: Product): Product {
        val entity = ProductMapper.toEntity(product)
        val savedEntity = productJpaRepository.save(entity)
        return ProductMapper.toDomain(savedEntity)
    }
}

