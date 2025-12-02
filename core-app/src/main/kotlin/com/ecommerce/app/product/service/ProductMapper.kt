package com.ecommerce.app.product.service

import com.ecommerce.app.product.port.`in`.CreateProductCommand
import com.ecommerce.app.product.port.`in`.CreateProductResult
import com.ecommerce.domain.product.Product
import java.math.BigDecimal

object ProductMapper {

    fun toProduct(command: CreateProductCommand): Product {
        return Product.create(
            name = command.name,
            description =  command.description,
            costPrice = BigDecimal(command.costPrice),
            salesPrice = BigDecimal(command.salesPrice),
        )
    }

    fun toCreateProductResult(product: Product): CreateProductResult {
        return CreateProductResult(
            productId = product.id.value,
            name = product.name.value,
            description = product.description.value,
            costPrice = product.price.costPrice.toInt(),
            salesPrice = product.price.salesPrice.toInt(),
            status = product.status,
        )
    }
}