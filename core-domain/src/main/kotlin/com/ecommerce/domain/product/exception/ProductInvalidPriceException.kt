package com.ecommerce.domain.product.exception

import com.ecommerce.domain.CoreException

class ProductInvalidPriceException(
    cause: Throwable? = null,
): CoreException("상품 가격은 0보다 커야 합니다.", cause) {
}