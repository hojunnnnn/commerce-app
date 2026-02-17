package com.ecommerce.app.order.service

import com.ecommerce.app.order.port.`in`.CreateOrderCommand
import com.ecommerce.app.order.port.`in`.CreateOrderResult
import com.ecommerce.app.order.port.`in`.CreateOrderUseCase
import com.ecommerce.app.product.service.ProductAccessor
import com.ecommerce.app.product.service.exception.ProductMismatchInOrderException
import com.ecommerce.app.product.service.exception.ProductNotFoundException
import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductStatus
import org.springframework.stereotype.Service

@Service
class CreateOrderService(
    private val orderKeyGenerator: OrderKeyGenerator,
    private val productAccessor: ProductAccessor,
    private val orderCreator: OrderCreator,
): CreateOrderUseCase {

    override fun create(command: CreateOrderCommand): CreateOrderResult {
        val newOrder = command.newOrder
        val orderProductIds = newOrder.items.map { it.productId.value }.toSet()
        val productMap =
            productAccessor.findByIdInAndStatus(orderProductIds, ProductStatus.ACTIVE).associateBy { it.id.value }

        validateOrderProduct(orderProductIds, productMap)

        val savedOrder = orderCreator.create(
            orderKey = orderKeyGenerator.generate(),
            newOrder = newOrder,
            productMap = productMap
        )
        return CreateOrderResult(savedOrder.orderKey.value)
    }

    private fun validateOrderProduct(
        orderProductIds: Set<Long>,
        productMap: Map<Long, Product>
    ) {
        if (productMap.isEmpty()) {
            throw ProductNotFoundException()
        }
        if(productMap.keys != orderProductIds) {
            throw ProductMismatchInOrderException()
        }
    }
}