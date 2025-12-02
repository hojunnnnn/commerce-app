package com.ecommerce.api.controller.product

import com.ecommerce.app.product.port.`in`.CreateProductCommand

data class CreateProductRequest(
    val name: String,
    val description: String,
    val costPrice: Int,
    val salesPrice: Int,
) {
    fun toCommand(): CreateProductCommand {
        return CreateProductCommand(
            name = name,
            description = description,
            costPrice = costPrice,
            salesPrice = salesPrice,
        )
    }
}