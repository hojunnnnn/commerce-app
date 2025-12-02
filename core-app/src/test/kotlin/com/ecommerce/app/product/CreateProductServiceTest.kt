package com.ecommerce.app.product

import com.ecommerce.app.product.port.`in`.CreateProductCommand
import com.ecommerce.app.product.service.CreateProductService
import com.ecommerce.app.product.service.ProductAccessor
import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CreateProductServiceTest {

    val productAccessor = mockk<ProductAccessor>()
    val createProductService = CreateProductService(productAccessor)

    @Test
    fun `상품 등록시 상품을 저장하고 상품 정보를 반환한다`() {
        val command = CreateProductCommand(
            name = "상품",
            description = "상품 설명",
            costPrice = 1000,
            salesPrice = 1500,
        )
        val product = Product.create(
            name = command.name,
            description = command.description,
            costPrice = BigDecimal(command.costPrice),
            salesPrice = BigDecimal(command.salesPrice),
        )

        every { productAccessor.save(product) } returns product

        val result = createProductService.create(command)

        assertThat(result.name).isEqualTo(command.name)
        assertThat(result.description).isEqualTo(command.description)
        assertThat(result.costPrice).isEqualTo(command.costPrice)
        assertThat(result.salesPrice).isEqualTo(command.salesPrice)
        assertThat(result.status).isEqualTo(ProductStatus.ACTIVE)

        verify(exactly = 1) { productAccessor.save(product) }
    }

}