package com.ecommerce.domain.product.exception

import com.ecommerce.domain.CoreException

class ProductDescriptionBlankException(
    cause: Throwable? = null
): CoreException("상품 설명은 공백으로만 이루어질 수 없습니다.", cause) {
}