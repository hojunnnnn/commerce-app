package com.ecommerce.domain.product.exception

import com.ecommerce.domain.CoreException

class ProductIdNegativeException(
    cause: Throwable? = null,
): CoreException("상품 ID는 음수일 수 없습니다,", cause)