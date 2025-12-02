package com.ecommerce.app.product.service

import com.ecommerce.app.product.port.`in`.CreateProductCommand
import com.ecommerce.app.product.port.`in`.CreateProductResult
import com.ecommerce.app.product.port.`in`.CreateProductUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateProductService(
    private val productAccessor: ProductAccessor,
): CreateProductUseCase {

    @Transactional
    override fun create(command: CreateProductCommand): CreateProductResult {
        val product = ProductMapper.toProduct(command)
        val savedProduct = productAccessor.save(product)
        return ProductMapper.toCreateProductResult(savedProduct)
    }
}