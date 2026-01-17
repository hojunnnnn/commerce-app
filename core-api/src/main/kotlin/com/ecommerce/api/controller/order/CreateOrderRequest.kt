package com.ecommerce.api.controller.order

import com.ecommerce.app.order.port.`in`.CreateOrderCommand
import com.ecommerce.domain.account.vo.AccountId
import com.ecommerce.domain.order.NewOrder
import com.ecommerce.domain.order.NewOrderItem
import com.ecommerce.domain.order.vo.OrderQuantity
import com.ecommerce.domain.product.vo.ProductId

data class CreateOrderRequest(
    val productId: Long,
    val quantity: Int
) {

    fun toCommand(accountId: Long): CreateOrderCommand {
        return CreateOrderCommand(
            newOrder = NewOrder(
                accountId = AccountId(accountId),
                items = listOf(
                    NewOrderItem(
                        productId = ProductId(productId),
                        quantity = OrderQuantity(quantity)
                    )
                )
            )
        )
    }
}