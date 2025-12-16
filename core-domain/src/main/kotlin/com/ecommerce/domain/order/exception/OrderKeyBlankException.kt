package com.ecommerce.domain.order.exception

import com.ecommerce.domain.CoreException

class OrderKeyBlankException(
    cause: Throwable? = null,
): CoreException("주문 키는 공백으로만 이루어질 수 없습니다.", cause) {
}