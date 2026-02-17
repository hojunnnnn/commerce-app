package com.ecommerce.app.product.port.out

import com.ecommerce.domain.product.Product
import com.ecommerce.domain.product.vo.ProductStatus

interface ProductRepository {

    fun save(product: Product): Product

    fun findByIdInAndStatus(productIds: Collection<Long>, status: ProductStatus): List<Product>
}