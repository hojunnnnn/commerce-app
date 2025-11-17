package com.ecommerce.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.ecommerce.api",
        "com.ecommerce.app",
        "com.ecommerce.infra"
    ]
)
class EcommerceApiApplication

fun main(args: Array<String>) {
    runApplication<EcommerceApiApplication>(*args)
}
