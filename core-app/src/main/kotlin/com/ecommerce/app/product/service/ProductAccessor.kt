package com.ecommerce.app.product.service

import com.ecommerce.app.product.port.out.ProductRepository
import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class ProductAccessor(
    private val productRepository: ProductRepository,
) {

    fun findByIdInAndStatus(
        orderProductIds: Collection<Long>,
        status: ProductStatus
    ): List<Product> {
        return productRepository.findByIdInAndStatus(orderProductIds, status)
    }

    @Transactional
    fun save(product: Product): Product {
        return productRepository.save(product)
    }

}