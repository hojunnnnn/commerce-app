package com.ecommerce.app.order.service

import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class OrderKeyGenerator {
    private val random = SecureRandom()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun generate(): String {
        val timestamp = LocalDateTime.now().format(formatter)
        val suffix = String.format("%08d", random.nextInt(100_000_000))
        return "$timestamp$suffix"
    }
}
