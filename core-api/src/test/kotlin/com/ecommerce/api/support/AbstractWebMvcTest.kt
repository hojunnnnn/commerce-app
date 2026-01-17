package com.ecommerce.api.support

import com.ecommerce.api.controller.account.AccountController
import com.ecommerce.api.controller.auth.AuthController
import com.ecommerce.api.controller.order.OrderController
import com.ecommerce.api.controller.product.ProductController
import com.ecommerce.app.account.port.`in`.SignupUseCase
import com.ecommerce.app.auth.port.`in`.GetAccountInfoUseCase
import com.ecommerce.app.auth.port.`in`.LoginUseCase
import com.ecommerce.app.order.port.`in`.CreateOrderUseCase
import com.ecommerce.app.product.port.`in`.CreateProductUseCase
import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc


@WebMvcTest(
    value = [
        AccountController::class,
        AuthController::class,
        ProductController::class,
        OrderController::class,
    ]
)
abstract class AbstractWebMvcTest {


    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var gson: Gson

    @MockkBean
    protected lateinit var signupUseCase: SignupUseCase

    @MockkBean
    protected lateinit var getAccountInfoUseCase: GetAccountInfoUseCase

    @MockkBean
    protected lateinit var loginUseCase: LoginUseCase

    @MockkBean
    protected lateinit var createProductUseCase: CreateProductUseCase

    @MockkBean
    protected lateinit var createOrderUseCase: CreateOrderUseCase


}