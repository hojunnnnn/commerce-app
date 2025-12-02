package com.ecommerce.app.product.port.`in`

data class CreateProductCommand(
    val name: String,
    val description: String,
    val costPrice: Int,
    val salesPrice: Int,
)