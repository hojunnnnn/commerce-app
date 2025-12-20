package com.ecommerce.app.order.port.`in`

interface CreateOrderUseCase {

    fun create(command: CreateOrderCommand): CreateOrderResult
}

