package com.ecommerce.app.order.service

import com.ecommerce.app.order.port.out.OrderItemRepository
import com.ecommerce.app.order.port.out.OrderRepository
import com.ecommerce.domain.order.NewOrder
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import com.ecommerce.domain.order.vo.OrderId
import com.ecommerce.domain.order.vo.OrderStatus
import com.ecommerce.domain.product.Product
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@Service
class OrderCreator(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository
) {

    fun create(
        orderKey: String,
        newOrder: NewOrder,
        productMap: Map<Long, Product>
    ): Order {
        val order = Order.create(
            accountId = newOrder.accountId.value,
            orderKey = orderKey,
            name = newOrder.items.first().let { productMap[it.productId.value]!!.name.value + if (newOrder.items.size > 1) " 외 ${newOrder.items.size - 1}개" else "" },
            price = newOrder.items.sumOf { productMap[it.productId.value]!!.price.salesPrice.multiply(BigDecimal.valueOf(it.quantity.value.toLong())) },
            status = OrderStatus.CREATED
        )
        val savedOrder = orderRepository.save(order)
        saveOrderItems(savedOrder.id, newOrder, productMap)
        return savedOrder
    }

    fun saveOrderItems(
        orderId: OrderId,
        newOrder: NewOrder,
        productMap: Map<Long, Product>
    ): List<OrderItem> {
        return orderItemRepository.saveAll(
            newOrder.items.map {
                val product = productMap[it.productId.value]!!
                OrderItem.create(
                    orderId = orderId,
                    productId = product.id,
                    quantity = it.quantity,
                    unitPrice = product.price.salesPrice,
                    totalPrice = product.price.salesPrice.multiply(BigDecimal.valueOf(it.quantity.value.toLong()))
                )
            }
        )
    }
}