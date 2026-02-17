package com.ecommerce.app.product.service.exception

import com.ecommerce.domain.CoreException

class ProductNotFoundException(
    cause: Throwable? = null,
): CoreException("존재하지 않는 상품입니다.")