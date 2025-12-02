package com.ecommerce.app.product.service

import com.ecommerce.app.product.port.out.ProductRepository
import com.ecommerce.domain.product.Product
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProductAccessor(
    private val productRepository: ProductRepository,
) {

    @Transactional
    fun save(product: Product): Product {
        return productRepository.save(product)
    }

}