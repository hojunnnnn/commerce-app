package com.ecommerce.domain.order

import com.ecommerce.domain.order.vo.OrderQuantity
import com.ecommerce.domain.product.vo.ProductId

data class NewOrderItem(
    val productId: ProductId,
    val quantity: OrderQuantity
)