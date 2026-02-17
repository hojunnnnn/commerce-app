package com.ecommerce.api.application.order

import com.ecommerce.api.support.AbstractIntegrationTest
import com.ecommerce.app.order.port.`in`.CreateOrderCommand
import com.ecommerce.app.order.port.out.OrderItemRepository
import com.ecommerce.app.order.port.out.OrderRepository
import com.ecommerce.app.order.service.CreateOrderService
import com.ecommerce.app.product.port.out.ProductRepository
import com.ecommerce.app.product.service.exception.ProductMismatchInOrderException
import com.ecommerce.app.product.service.exception.ProductNotFoundException
import com.ecommerce.domain.account.vo.AccountId
import com.ecommerce.domain.order.NewOrder
import com.ecommerce.domain.order.NewOrderItem
import com.ecommerce.domain.order.vo.OrderQuantity
import com.ecommerce.domain.order.vo.OrderStatus
import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductId
import com.ecommerce.domain.product.vo.ProductStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class OrderServiceIntegrationTest(
    private val createOrderService: CreateOrderService,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
): AbstractIntegrationTest() {

    @Test
    fun `주문을 생성하면 주문과 주문 아이템이 저장된다`() {
        val laptop = saveProduct(
            name = "노트북",
            description = "노트북 설명",
            costPrice = BigDecimal("1000000"),
            salesPrice = BigDecimal("1200000"),
            status = ProductStatus.ACTIVE
        )
        val mouse = saveProduct(
            name = "마우스",
            description = "마우스 설명",
            costPrice = BigDecimal("20000"),
            salesPrice = BigDecimal("30000"),
            status = ProductStatus.ACTIVE
        )
        val command = createCommand(
            accountId = 1L,
            items = listOf(
                NewOrderItem(productId = laptop.id, quantity = OrderQuantity(2)),
                NewOrderItem(productId = mouse.id, quantity = OrderQuantity(1))
            )
        )

        val result = createOrderService.create(command)

        assertThat(result.orderKey).isNotBlank
        assertThat(result.orderKey).hasSize(16)

        val savedOrder = orderRepository.findAll().single()
        assertThat(savedOrder.orderKey.value).isEqualTo(result.orderKey)
        assertThat(savedOrder.accountId.value).isEqualTo(1L)
        assertThat(savedOrder.name.value).isEqualTo("노트북 외 1개")
        assertThat(savedOrder.price.value).isEqualByComparingTo(BigDecimal(2430000))
        assertThat(savedOrder.status).isEqualTo(OrderStatus.CREATED)

        val savedItems = orderItemRepository.findAllByOrderId(savedOrder.id.value)
        assertThat(savedItems).hasSize(2)

        val laptopItem = savedItems.single { it.productId.value == laptop.id.value }
        assertThat(laptopItem.orderId).isEqualTo(savedOrder.id)
        assertThat(laptopItem.quantity.value).isEqualTo(2)
        assertThat(laptopItem.price.unitPrice).isEqualByComparingTo(BigDecimal(1200000))
        assertThat(laptopItem.price.totalPrice).isEqualByComparingTo(BigDecimal(2400000))

        val mouseItem = savedItems.single { it.productId.value == mouse.id.value }
        assertThat(mouseItem.orderId).isEqualTo(savedOrder.id)
        assertThat(mouseItem.quantity.value).isEqualTo(1)
        assertThat(mouseItem.price.unitPrice).isEqualByComparingTo(BigDecimal(30000))
        assertThat(mouseItem.price.totalPrice).isEqualByComparingTo(BigDecimal(30000))
    }

    @Test
    fun `주문 상품이 존재하지 않으면 ProductNotFoundException 이 발생한다`() {
        val command = createCommand(
            accountId = 1L,
            items = listOf(
                NewOrderItem(productId = ProductId(9999L), quantity = OrderQuantity(1))
            )
        )

        assertThrows<ProductNotFoundException> { createOrderService.create(command) }
    }

    @Test
    fun `주문 상품 중 일부만 활성 상태이면 ProductMismatchInOrderException 이 발생한다`() {
        val activeProduct = saveProduct(
            name = "활성 상품",
            description = "활성 상품 설명",
            costPrice = BigDecimal("1000"),
            salesPrice = BigDecimal("1500"),
            status = ProductStatus.ACTIVE
        )
        val deletedProduct = saveProduct(
            name = "삭제 상품",
            description = "삭제 상품 설명",
            costPrice = BigDecimal("1000"),
            salesPrice = BigDecimal("1500"),
            status = ProductStatus.DELETED
        )
        val command = createCommand(
            accountId = 2L,
            items = listOf(
                NewOrderItem(productId = activeProduct.id, quantity = OrderQuantity(1)),
                NewOrderItem(productId = deletedProduct.id, quantity = OrderQuantity(1))
            )
        )

        assertThrows<ProductMismatchInOrderException> { createOrderService.create(command) }
    }

    private fun createCommand(accountId: Long, items: List<NewOrderItem>): CreateOrderCommand {
        return CreateOrderCommand(
            newOrder = NewOrder(
                accountId = AccountId(accountId),
                items = items
            )
        )
    }

    private fun saveProduct(
        name: String,
        description: String,
        costPrice: BigDecimal,
        salesPrice: BigDecimal,
        status: ProductStatus
    ): Product {
        return productRepository.save(
            Product.create(
                name = name,
                description = description,
                costPrice = costPrice,
                salesPrice = salesPrice,
                status = status
            )
        )
    }
}
