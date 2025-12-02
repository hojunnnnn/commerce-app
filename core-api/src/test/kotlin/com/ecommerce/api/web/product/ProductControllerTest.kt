package com.ecommerce.api.web.product

import com.ecommerce.api.controller.product.CreateProductRequest
import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractRestDocumentTest
import com.ecommerce.api.support.WithMockAccount
import com.ecommerce.app.product.port.`in`.CreateProductResult
import com.ecommerce.domain.product.vo.ProductStatus
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.epages.restdocs.apispec.Schema
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProductControllerTest: AbstractRestDocumentTest() {

    @Test
    @WithMockAccount
    fun `상품 등록 성공`() {
        val request = CreateProductRequest(
            name = "테스트 상품",
            description = "테스트 상품 설명",
            costPrice = 1000,
            salesPrice = 1500,
        )

        val createProductResult = CreateProductResult(
            productId = 1L,
            name = "테스트 상품",
            description = "테스트 상품 설명",
            costPrice = 1000,
            salesPrice = 1500,
            status = ProductStatus.ACTIVE
        )

        every { createProductUseCase.create(any()) } returns createProductResult

        val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .withAuthHeader()
                .content(gson.toJson(request))
        )

        resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$.result").value(ResultType.SUCCESS.name),
            jsonPath("$.status").value(200),
            jsonPath("$.data.name").value(createProductResult.name),
            jsonPath("$.data.description").value(createProductResult.description),
            jsonPath("$.data.costPrice").value(createProductResult.costPrice),
            jsonPath("$.data.salesPrice").value(createProductResult.salesPrice),
            jsonPath("$.data.status").value(createProductResult.status.name),
            jsonPath("$.error").value(null),

        )

        resultActions.andDocument(
            "상품 등록 - 성공",
            ResourceSnippetParameters.builder()
                .tag("Product")
                .summary("상품 등록")
                .description("상품 등록 API")
                .requestSchema(Schema("CreateProductRequest"))
                .requestFields(*createProductRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<CreateProductResult>"))
                .responseFields(*createProductSuccessResponseFields().toTypedArray())
                .build()
        )
    }

    private fun createProductRequestFields() = listOf(
        fieldWithPath("name").description("상품 이름"),
        fieldWithPath("description").description("상품 설명"),
        fieldWithPath("costPrice").description("원가"),
        fieldWithPath("salesPrice").description("판매가"),
    )

    private fun createProductSuccessResponseFields() = commonSuccessResponseFields(
        fieldWithPath("data.productId").description("상품 ID"),
        fieldWithPath("data.name").description("상품 이름"),
        fieldWithPath("data.description").description("상품 설명"),
        fieldWithPath("data.costPrice").description("원가"),
        fieldWithPath("data.salesPrice").description("판매가"),
        fieldWithPath("data.status").description("상품 상태"),
    )
}