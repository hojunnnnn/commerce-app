package com.ecommerce.api.application.product

import com.ecommerce.api.support.AbstractIntegrationTest
import com.ecommerce.app.product.port.`in`.CreateProductCommand
import com.ecommerce.app.product.service.CreateProductService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class CreateProductServiceIntegrationTest(
    private val createProductService: CreateProductService,
): AbstractIntegrationTest() {

    @Test
    fun `상품을 등록할 수 있다`() {
        val command = CreateProductCommand(
            name = "테스트 상품",
            description = "테스트 상품 설명",
            costPrice = 1000,
            salesPrice = 1500,
        )

        val result = createProductService.create(command)

        assertThat(result.name).isEqualTo(command.name)
        assertThat(result.description).isEqualTo(command.description)
        assertThat(result.costPrice).isEqualTo(command.costPrice)
        assertThat(result.salesPrice).isEqualTo(command.salesPrice)
    }
}