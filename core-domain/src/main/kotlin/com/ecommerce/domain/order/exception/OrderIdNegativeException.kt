package com.ecommerce.domain.order.exception

import com.ecommerce.domain.CoreException

class OrderIdNegativeException(
cause: Throwable? = null,
): CoreException("주문 ID는 음수일 수 없습니다.", cause)