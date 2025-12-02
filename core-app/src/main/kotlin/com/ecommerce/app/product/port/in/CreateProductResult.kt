package com.ecommerce.app.product.port.`in`

import com.ecommerce.domain.product.vo.ProductStatus

data class CreateProductResult(
    val productId: Long,
    val name: String,
    val description: String,
    val costPrice: Int,
    val salesPrice: Int,
    val status: ProductStatus,
)