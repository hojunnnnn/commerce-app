package com.ecommerce.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductTest {

    @Test
    fun `상품을 생성할 수 있다`() {
        val name = "테스트 상품"
        val description = "테스트 내용"
        val costPrice = BigDecimal(1000)
        val salesPrice = BigDecimal(1500)

        val product = Product.create(
            name = name,
            description = description,
            costPrice = costPrice,
            salesPrice = salesPrice
        )

        assertThat(product.name.value).isEqualTo(name)
        assertThat(product.description.value).isEqualTo(description)
        assertThat(product.price.costPrice).isEqualTo(costPrice)
        assertThat(product.price.salesPrice).isEqualTo(salesPrice)
    }
}