package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductDescriptionBlankException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductDescriptionTest {

    @Test
    fun `상품 설명이 공백이면 예외가 발생한다`() {
        assertThrows<ProductDescriptionBlankException> { ProductDescription("   ") }
    }
}