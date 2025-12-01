package com.ecommerce.domain.product.vo

import com.ecommerce.domain.product.exception.ProductInvalidPriceException
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class ProductPriceTest {

    @ParameterizedTest
    @ValueSource(ints = [0, -1])
    fun `상품 가격이 0 또는 음수이면 예외가 발생한다`(price: Int) {
        val costPrice = BigDecimal(price)
        val salesPrice = BigDecimal(price)
        assertThrows<ProductInvalidPriceException> { ProductPrice(costPrice, salesPrice) }
    }
}