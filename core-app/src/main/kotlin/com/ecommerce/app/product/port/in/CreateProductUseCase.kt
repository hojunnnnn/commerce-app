package com.ecommerce.app.product.port.`in`

interface CreateProductUseCase {

    fun create(command: CreateProductCommand): CreateProductResult
}

