package com.ecommerce.domain.order

import com.ecommerce.domain.order.vo.OrderId
import com.ecommerce.domain.order.vo.OrderKey
import com.ecommerce.domain.order.vo.OrderName
import com.ecommerce.domain.order.vo.OrderPrice
import com.ecommerce.domain.order.vo.OrderStatus
import java.math.BigDecimal


data class Order(
    val id: OrderId,
    val orderKey: OrderKey,
    val name: OrderName,
    val price: OrderPrice,
    val status: OrderStatus,
) {

    companion object {
        fun create(
            orderKey: String,
            name: String,
            price: BigDecimal,
            status: OrderStatus = OrderStatus.CREATED,
        ): Order {
            return Order(
                id = OrderId.generate(),
                orderKey = OrderKey(orderKey),
                name = OrderName(name),
                price = OrderPrice(price),
                status = status,
            )
        }
    }
}



