package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductDescriptionBlankException

@JvmInline
value class ProductDescription(val value: String) {

    init {
        if (value.isBlank()) {
            throw ProductDescriptionBlankException()
        }
    }
}