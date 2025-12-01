package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductIdNegativeException

@JvmInline
value class ProductId(val value: Long) {

    init {
        if (value < 0) {
            throw ProductIdNegativeException()
        }
    }

    companion object {
        fun generate(value: Long = 0L): ProductId = ProductId(value)
    }
}