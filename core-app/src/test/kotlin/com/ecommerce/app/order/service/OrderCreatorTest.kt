package com.ecommerce.app.order.service

import com.ecommerce.app.order.port.out.OrderItemRepository
import com.ecommerce.app.order.port.out.OrderRepository
import com.ecommerce.domain.account.vo.AccountId
import com.ecommerce.domain.order.NewOrder
import com.ecommerce.domain.order.NewOrderItem
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.OrderItem
import com.ecommerce.domain.order.vo.OrderId
import com.ecommerce.domain.order.vo.OrderQuantity
import com.ecommerce.domain.order.vo.OrderStatus
import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductId
import com.ecommerce.domain.product.vo.ProductStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderCreatorTest {

    val orderRepository = mockk<OrderRepository>()
    val orderItemRepository = mockk<OrderItemRepository>()

    val orderCreator = OrderCreator(
        orderRepository,
        orderItemRepository
    )

    private val accountId = 1L
    private val laptopProductId = 101L
    private val mouseProductId = 102L
    private val laptopPrice = BigDecimal("1200000")
    private val mousePrice = BigDecimal("30000")

    @Test
    fun `주문 생성시 주문명을 조합하고 총 주문 금액을 계산해 저장한다`() {
        val orderKey = "2026021700000001"
        val newOrder = NewOrder(
            accountId = AccountId(accountId),
            items = listOf(
                NewOrderItem(productId = ProductId(laptopProductId), quantity = OrderQuantity(2)),
                NewOrderItem(productId = ProductId(mouseProductId), quantity = OrderQuantity(1)),
            ),
        )
        val productMap = mapOf(
            laptopProductId to Product.reconstruct(
                id = laptopProductId,
                name = "노트북",
                description = "노트북 설명",
                costPrice = BigDecimal("1000000"),
                salesPrice = laptopPrice,
                status = ProductStatus.ACTIVE,
            ),
            mouseProductId to Product.reconstruct(
                id = mouseProductId,
                name = "마우스",
                description = "마우스 설명",
                costPrice = BigDecimal("20000"),
                salesPrice = mousePrice,
                status = ProductStatus.ACTIVE,
            ),
        )
        every { orderRepository.save(any()) } answers { firstArg<Order>() }

        val result = orderCreator.create(orderKey, newOrder, productMap)

        assertThat(result.orderKey.value).isEqualTo(orderKey)
        assertThat(result.accountId.value).isEqualTo(accountId)
        assertThat(result.name.value).isEqualTo("노트북 외 1개")
        assertThat(result.price.value).isEqualTo(BigDecimal("2430000"))
        assertThat(result.status).isEqualTo(OrderStatus.CREATED)

        verify(exactly = 1) {
            orderRepository.save(
                match {
                    it.orderKey.value == orderKey &&
                        it.accountId.value == accountId &&
                        it.name.value == "노트북 외 1개" &&
                        it.price.value.compareTo(BigDecimal("2430000")) == 0 &&
                        it.status == OrderStatus.CREATED
                }
            )
        }
    }

    @Test
    fun `주문 아이템 저장시 각 아이템의 단가와 총 금액을 계산해 저장한다`() {
        val orderId = OrderId.generate(99L)
        val newOrder = NewOrder(
            accountId = AccountId(accountId),
            items = listOf(
                NewOrderItem(productId = ProductId(laptopProductId), quantity = OrderQuantity(3)),
                NewOrderItem(productId = ProductId(mouseProductId), quantity = OrderQuantity(2)),
            ),
        )
        val productMap = mapOf(
            laptopProductId to Product.reconstruct(
                id = laptopProductId,
                name = "노트북",
                description = "노트북 설명",
                costPrice = BigDecimal("1000000"),
                salesPrice = laptopPrice,
                status = ProductStatus.ACTIVE,
            ),
            mouseProductId to Product.reconstruct(
                id = mouseProductId,
                name = "마우스",
                description = "마우스 설명",
                costPrice = BigDecimal("20000"),
                salesPrice = mousePrice,
                status = ProductStatus.ACTIVE,
            ),
        )

        every { orderItemRepository.saveAll(any()) } answers { firstArg<List<OrderItem>>() }

        val result = orderCreator.saveOrderItems(orderId, newOrder, productMap)

        assertThat(result).hasSize(2)
        assertThat(result[0].orderId.value).isEqualTo(99L)
        assertThat(result[0].productId.value).isEqualTo(laptopProductId)
        assertThat(result[0].quantity.value).isEqualTo(3)
        assertThat(result[0].price.unitPrice).isEqualTo(laptopPrice)
        assertThat(result[0].price.totalPrice).isEqualTo(BigDecimal("3600000"))
        assertThat(result[1].orderId.value).isEqualTo(99L)
        assertThat(result[1].productId.value).isEqualTo(mouseProductId)
        assertThat(result[1].quantity.value).isEqualTo(2)
        assertThat(result[1].price.unitPrice).isEqualTo(mousePrice)
        assertThat(result[1].price.totalPrice).isEqualTo(BigDecimal("60000"))

        verify(exactly = 1) {
            orderItemRepository.saveAll(
                match { items ->
                    items.size == 2 &&
                        items[0].orderId.value == 99L &&
                        items[0].productId.value == laptopProductId &&
                        items[0].price.totalPrice.compareTo(BigDecimal("3600000")) == 0 &&
                        items[1].orderId.value == 99L &&
                        items[1].productId.value == mouseProductId &&
                        items[1].price.totalPrice.compareTo(BigDecimal("60000")) == 0
                }
            )
        }
    }
}
