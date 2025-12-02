package com.ecommerce.domain.product

import com.ecommerce.domain.product.vo.ProductPrice
import com.ecommerce.domain.product.vo.ProductDescription
import com.ecommerce.domain.product.vo.ProductId
import com.ecommerce.domain.product.vo.ProductName
import com.ecommerce.domain.product.vo.ProductStatus
import java.math.BigDecimal


data class Product(
    val id: ProductId,
    val name: ProductName,
    val description: ProductDescription,
    val price: ProductPrice,
    val status: ProductStatus,
) {

    companion object {
        fun create(
            name: String,
            description: String,
            costPrice: BigDecimal,
            salesPrice: BigDecimal,
            status: ProductStatus = ProductStatus.ACTIVE,
        ): Product {
            return Product(
                id = ProductId.generate(),
                name = ProductName(name),
                description = ProductDescription(description),
                price = ProductPrice(
                    costPrice = costPrice,
                    salesPrice = salesPrice
                ),
                status = status,
            )
        }

        fun reconstruct(
            id: Long,
            name: String,
            description: String,
            costPrice: BigDecimal,
            salesPrice: BigDecimal,
            status: ProductStatus,
        ): Product {
            return Product(
                id = ProductId(id),
                name = ProductName(name),
                description = ProductDescription(description),
                price = ProductPrice(
                    costPrice = costPrice,
                    salesPrice = salesPrice
                ),
                status = status,
            )
        }
    }
}