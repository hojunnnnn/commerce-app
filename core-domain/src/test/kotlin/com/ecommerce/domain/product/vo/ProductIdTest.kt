package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductIdNegativeException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductIdTest {

    @Test
    fun `상품 아이디는 양수여야 한다`() {
        val id = 10L
        val productId = ProductId(id)

        assertThat(productId.value).isEqualTo(id)
    }

    @Test
    fun `상품 아이디가 음수이면 예외가 발생한다`() {
        assertThrows<ProductIdNegativeException> { ProductId(-1) }
    }

}