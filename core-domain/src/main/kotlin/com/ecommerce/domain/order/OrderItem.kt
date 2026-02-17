package com.ecommerce.domain.order

import com.ecommerce.domain.order.vo.OrderId
import com.ecommerce.domain.order.vo.OrderItemId
import com.ecommerce.domain.order.vo.OrderItemPrice
import com.ecommerce.domain.order.vo.OrderQuantity
import com.ecommerce.domain.product.vo.ProductId
import java.math.BigDecimal

data class OrderItem(
    val id: OrderItemId,
    val orderId: OrderId,
    val productId: ProductId,
    val quantity: OrderQuantity,
    val price: OrderItemPrice,
) {

    companion object {
        fun create(
            orderId: OrderId,
            productId: ProductId,
            quantity: OrderQuantity,
            unitPrice: BigDecimal,
            totalPrice: BigDecimal,
        ): OrderItem {
            return OrderItem(
                id = OrderItemId.generate(),
                orderId = orderId,
                productId = productId,
                quantity = quantity,
                price = OrderItemPrice(unitPrice, totalPrice),
            )
        }

        fun reconstruct(
            id: Long,
            orderId: Long,
            productId: Long,
            quantity: Int,
            unitPrice: BigDecimal,
            totalPrice: BigDecimal,
        ): OrderItem {
            return OrderItem(
                id = OrderItemId(id),
                orderId = OrderId(orderId),
                productId = ProductId(productId),
                quantity = OrderQuantity(quantity),
                price = OrderItemPrice(unitPrice, totalPrice),
            )
        }
    }
}

