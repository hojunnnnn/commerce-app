package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductNameBlankException

@JvmInline
value class ProductName(val value: String) {

    init {
        if (value.isBlank()) {
            throw ProductNameBlankException()
        }
    }
}


