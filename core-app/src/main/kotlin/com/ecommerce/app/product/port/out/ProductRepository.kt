package com.ecommerce.app.product.port.out

import com.ecommerce.domain.product.Product

interface ProductRepository {

    fun save(product: Product): Product
}