package com.ecommerce.domain.order

import com.ecommerce.domain.account.vo.AccountId
import com.ecommerce.domain.order.vo.OrderId
import com.ecommerce.domain.order.vo.OrderKey
import com.ecommerce.domain.order.vo.OrderName
import com.ecommerce.domain.order.vo.OrderPrice
import com.ecommerce.domain.order.vo.OrderStatus
import java.math.BigDecimal


data class Order(
    val id: OrderId,
    val orderKey: OrderKey,
    val accountId: AccountId,
    val name: OrderName,
    val price: OrderPrice,
    val status: OrderStatus,
) {

    companion object {
        fun create(
            accountId: Long,
            orderKey: String,
            name: String,
            price: BigDecimal,
            status: OrderStatus = OrderStatus.CREATED,
        ): Order {
            return Order(
                id = OrderId.generate(),
                orderKey = OrderKey(orderKey),
                accountId = AccountId(accountId),
                name = OrderName(name),
                price = OrderPrice(price),
                status = status,
            )
        }

        fun reconstruct(
            id: Long,
            orderKey: String,
            accountId: Long,
            name: String,
            price: BigDecimal,
            status: OrderStatus,
        ): Order {
            return Order(
                id = OrderId(id),
                orderKey = OrderKey(orderKey),
                accountId = AccountId(accountId),
                name = OrderName(name),
                price = OrderPrice(price),
                status = status,
            )
        }
    }
}


