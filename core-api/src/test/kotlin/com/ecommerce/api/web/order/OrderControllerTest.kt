package com.ecommerce.api.web.order

import com.ecommerce.api.controller.order.CreateOrderRequest
import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractRestDocumentTest
import com.ecommerce.api.support.WithMockAccount
import com.ecommerce.app.order.port.`in`.CreateOrderResult
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.epages.restdocs.apispec.Schema
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class OrderControllerTest: AbstractRestDocumentTest() {


    @Test
    @WithMockAccount
    fun `주문 성공`() {
        val request = CreateOrderRequest(
            productId = 1L,
            quantity = 1
        )

        val createOrderResult = CreateOrderResult(
            orderKey = "20260117153212345"
        )

        every { createOrderUseCase.create(any()) } returns createOrderResult

        val resultAction = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .withAuthHeader()
                .content(gson.toJson(request))
        )

        resultAction.andExpectAll(
            status().isOk(),
            jsonPath("$.result").value(ResultType.SUCCESS.name),
            jsonPath("$.status").value(200),
            jsonPath("$.data.orderKey").value(createOrderResult.orderKey),
            jsonPath("$.error").value(null),
        )

        resultAction.andDocument(
            "주문 등록 - 성공",
            ResourceSnippetParameters.builder()
                .tag("Order")
                .summary("주문 등록")
                .description("주문 등록 API")
                .requestSchema(Schema("CreateOrderRequest"))
                .requestFields(*createOrderRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<CreateOrderResult>"))
                .responseFields(*createOrderSuccessResponseFields().toTypedArray())
                .build()
        )
    }

    private fun createOrderRequestFields() = listOf(
        fieldWithPath("productId").description("상품 ID"),
        fieldWithPath("quantity").description("상품 수량"),
    )

    private fun createOrderSuccessResponseFields() = commonSuccessResponseFields(
        fieldWithPath("data.orderKey").description("주문키")
    )


}