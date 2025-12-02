package com.ecommerce.api.controller.product

import com.ecommerce.api.response.ApiResponse
import com.ecommerce.app.product.port.`in`.CreateProductResult
import com.ecommerce.app.product.port.`in`.CreateProductUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val createProductUseCase: CreateProductUseCase
) {

    @PostMapping("/api/v1/products")
    fun createProduct(
        @RequestBody req: CreateProductRequest,
    ): ApiResponse<CreateProductResult> {
        val command = req.toCommand()
        val result = createProductUseCase.create(command)
        return ApiResponse.success(result)
    }

}

