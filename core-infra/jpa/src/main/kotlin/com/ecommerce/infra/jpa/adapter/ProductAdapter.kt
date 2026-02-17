package com.ecommerce.infra.jpa.adapter

import com.ecommerce.app.product.port.out.ProductRepository
import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductId
import com.ecommerce.domain.product.vo.ProductStatus
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

    override fun findByIdInAndStatus(
        productIds: Collection<Long>,
        status: ProductStatus
    ): List<Product> {
        val productEntities = productJpaRepository.findByIdInAndStatus(productIds, status)
        return productEntities.map { ProductMapper.toDomain(it) }
    }
}

