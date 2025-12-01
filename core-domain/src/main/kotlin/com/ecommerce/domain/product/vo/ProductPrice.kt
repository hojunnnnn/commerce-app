package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductInvalidPriceException
import java.math.BigDecimal

data class ProductPrice(
    val costPrice: BigDecimal,
    val salesPrice: BigDecimal,
) {

    init {
        if (costPrice <= BigDecimal.ZERO) {
            throw ProductInvalidPriceException()
        }
        if (salesPrice <= BigDecimal.ZERO) {
            throw ProductInvalidPriceException()
        }
    }
}