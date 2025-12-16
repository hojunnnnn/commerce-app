package com.ecommerce.domain.order.exception

import com.ecommerce.domain.CoreException

class OrderInvalidQuantityException(
    cause: Throwable? = null,
): CoreException("주문 수량은 0보다 커야 합니다.", cause)