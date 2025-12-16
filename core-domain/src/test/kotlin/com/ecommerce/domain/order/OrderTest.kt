package com.ecommerce.domain.order

import com.ecommerce.domain.order.vo.OrderStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderTest {

    @Test
    fun `주문을 생성할 수 있다`() {
        val orderKey = "order-123"
        val name = "주문123"
        val price = BigDecimal(1000)

        val order = Order.create(orderKey, name, price)

        assertThat(order.orderKey.value).isEqualTo(orderKey)
        assertThat(order.name.value).isEqualTo(name)
        assertThat(order.price.value).isEqualTo(price)
        assertThat(order.status).isEqualTo(OrderStatus.CREATED)

    }
}