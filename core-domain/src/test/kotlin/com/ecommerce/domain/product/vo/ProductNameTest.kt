package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductNameBlankException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductNameTest {

    @Test
    fun `상품 이름이 공백이면 예외가 발생한다`() {
        assertThrows<ProductNameBlankException> { ProductName("   ") }
    }
}