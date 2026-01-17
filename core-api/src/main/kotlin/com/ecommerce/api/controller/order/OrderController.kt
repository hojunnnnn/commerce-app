package com.ecommerce.api.controller.order

import com.ecommerce.api.response.ApiResponse
import com.ecommerce.api.security.AccountPrincipal
import com.ecommerce.app.order.port.`in`.CreateOrderResult
import com.ecommerce.app.order.port.`in`.CreateOrderUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val createOrderUseCase: CreateOrderUseCase
) {

    @PostMapping("/api/v1/orders")
    fun create(
        @AuthenticationPrincipal account: AccountPrincipal,
        @RequestBody req: CreateOrderRequest,
    ): ApiResponse<CreateOrderResult> {
        val command = req.toCommand(account.id)
        return ApiResponse.success(createOrderUseCase.create(command))
    }


    // NOTE: Cart order?(여러 상품 한 번에 주문)
}

