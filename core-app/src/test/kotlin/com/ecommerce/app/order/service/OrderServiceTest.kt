package com.ecommerce.app.order.service

import com.ecommerce.app.order.port.`in`.CreateOrderCommand
import com.ecommerce.app.product.service.ProductAccessor
import com.ecommerce.app.product.service.exception.ProductMismatchInOrderException
import com.ecommerce.app.product.service.exception.ProductNotFoundException
import com.ecommerce.domain.account.vo.AccountId
import com.ecommerce.domain.order.NewOrder
import com.ecommerce.domain.order.NewOrderItem
import com.ecommerce.domain.order.Order
import com.ecommerce.domain.order.vo.OrderQuantity
import com.ecommerce.domain.order.vo.OrderStatus
import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductId
import com.ecommerce.domain.product.vo.ProductStatus
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class OrderServiceTest {

    private val orderKeyGenerator = mockk<OrderKeyGenerator>()
    private val productAccessor = mockk<ProductAccessor>()
    private val orderCreator = mockk<OrderCreator>()

    private val createOrderService = CreateOrderService(
        orderKeyGenerator = orderKeyGenerator,
        productAccessor = productAccessor,
        orderCreator = orderCreator
    )

    @Test
    fun `주문 생성시 주문 키를 생성하고 주문과 주문 아이템을 저장한 뒤 주문 키를 반환한다`() {
        val command = createCommand()
        val generatedOrderKey = "2026021700000001"
        val productMap = createProducts().associateBy { it.id.value }
        val savedOrder = Order.reconstruct(
            id = 10L,
            orderKey = generatedOrderKey,
            accountId = command.newOrder.accountId.value,
            name = "노트북 외 1개",
            price = BigDecimal("2430000"),
            status = OrderStatus.CREATED
        )

        every { productAccessor.findByIdInAndStatus(setOf(101L, 102L), ProductStatus.ACTIVE) } returns productMap.values.toList()
        every { orderKeyGenerator.generate() } returns generatedOrderKey
        every { orderCreator.create(generatedOrderKey, command.newOrder, productMap) } returns savedOrder
        every { orderCreator.saveOrderItems(savedOrder.id, command.newOrder, productMap) } returns emptyList()

        val result = createOrderService.create(command)

        assertThat(result.orderKey).isEqualTo(generatedOrderKey)

        verify(exactly = 1) { productAccessor.findByIdInAndStatus(setOf(101L, 102L), ProductStatus.ACTIVE) }
        verify(exactly = 1) { orderKeyGenerator.generate() }
        verify(exactly = 1) { orderCreator.create(generatedOrderKey, command.newOrder, productMap) }
        verify(exactly = 1) { orderCreator.saveOrderItems(savedOrder.id, command.newOrder, productMap) }
    }

    @Test
    fun `주문 상품 조회 결과가 비어있으면 ProductNotFoundException 이 발생한다`() {
        val command = createCommand()
        every {
            productAccessor.findByIdInAndStatus(setOf(101L, 102L), ProductStatus.ACTIVE)
        } returns emptyList()

        assertThrows<ProductNotFoundException> { createOrderService.create(command) }

        verify(exactly = 1) { productAccessor.findByIdInAndStatus(setOf(101L, 102L), ProductStatus.ACTIVE) }
        verify(exactly = 0) { orderKeyGenerator.generate() }
        verify { orderCreator wasNot Called } // create(), saveOrderItems()
    }

    @Test
    fun `주문 상품 중 일부만 조회되면 ProductMismatchInOrderException 이 발생한다`() {
        val command = createCommand()
        val onlyOneProduct = listOf(
            Product.reconstruct(
                id = 101L,
                name = "노트북",
                description = "노트북 설명",
                costPrice = BigDecimal("1000000"),
                salesPrice = BigDecimal("1200000"),
                status = ProductStatus.ACTIVE
            )
        )
        every {
            productAccessor.findByIdInAndStatus(setOf(101L, 102L), ProductStatus.ACTIVE)
        } returns onlyOneProduct

        assertThrows<ProductMismatchInOrderException> { createOrderService.create(command) }

        verify(exactly = 1) { productAccessor.findByIdInAndStatus(setOf(101L, 102L), ProductStatus.ACTIVE) }
        verify(exactly = 0) { orderKeyGenerator.generate() }
        verify { orderCreator wasNot Called }
    }

    private fun createCommand(): CreateOrderCommand {
        return CreateOrderCommand(
            newOrder = NewOrder(
                accountId = AccountId(1L),
                items = listOf(
                    NewOrderItem(productId = ProductId(101L), quantity = OrderQuantity(2)),
                    NewOrderItem(productId = ProductId(102L), quantity = OrderQuantity(1))
                )
            )
        )
    }

    private fun createProducts(): List<Product> {
        return listOf(
            Product.reconstruct(
                id = 101L,
                name = "노트북",
                description = "노트북 설명",
                costPrice = BigDecimal("1000000"),
                salesPrice = BigDecimal("1200000"),
                status = ProductStatus.ACTIVE
            ),
            Product.reconstruct(
                id = 102L,
                name = "마우스",
                description = "마우스 설명",
                costPrice = BigDecimal("20000"),
                salesPrice = BigDecimal("30000"),
                status = ProductStatus.ACTIVE
            )
        )
    }
}
