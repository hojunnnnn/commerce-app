package com.ecommerce.domain.order

import com.ecommerce.domain.order.vo.OrderId
import com.ecommerce.domain.order.vo.OrderKey
import com.ecommerce.domain.order.vo.OrderName
import com.ecommerce.domain.order.vo.OrderPrice
import com.ecommerce.domain.order.vo.OrderStatus


data class Order(
    val id: OrderId,
    val orderKey: OrderKey,
    val name: OrderName,
    val price: OrderPrice,
    val status: OrderStatus,
) {
}



