package com.ecommerce.domain.order

import com.ecommerce.domain.order.vo.OrderId
import com.ecommerce.domain.order.vo.OrderItemId
import com.ecommerce.domain.order.vo.OrderItemPrice
import com.ecommerce.domain.order.vo.OrderQuantity
import com.ecommerce.domain.product.vo.ProductId

data class OrderItem(
    val id: OrderItemId,
    val orderId: OrderId,
    val productId: ProductId,
    val quantity: OrderQuantity,
    val price: OrderItemPrice,
) {
}


