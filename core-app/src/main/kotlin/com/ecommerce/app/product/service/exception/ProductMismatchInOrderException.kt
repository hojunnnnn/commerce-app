package com.ecommerce.app.product.service.exception

import com.ecommerce.domain.CoreException

class ProductMismatchInOrderException(
): CoreException("요청한 상품 정보와 일치하지 않습니다.")