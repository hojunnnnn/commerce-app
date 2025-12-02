package com.ecommerce.infra.jpa.adapter.mapper

import com.ecommerce.domain.product.Product
import com.ecommerce.infra.jpa.entity.ProductEntity

object ProductMapper {

    fun toDomain(entity: ProductEntity): Product {
        return Product.reconstruct(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            costPrice = entity.costPrice,
            salesPrice = entity.salesPrice,
            status = entity.status
        )
    }

    fun toEntity(product: Product): ProductEntity {
        return ProductEntity(
            name = product.name.value,
            description = product.description.value,
            costPrice = product.price.costPrice,
            salesPrice = product.price.salesPrice,
            status = product.status
        )
    }
}