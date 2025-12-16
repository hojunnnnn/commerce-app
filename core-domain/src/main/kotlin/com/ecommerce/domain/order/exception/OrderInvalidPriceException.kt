package com.ecommerce.domain.order.exception

import com.ecommerce.domain.CoreException

class OrderInvalidPriceException(
    cause: Throwable? = null,
): CoreException("주문 금액은 0보다 커야 합니다.", cause) {
}