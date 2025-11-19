package com.ecommerce.infra.auth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val secretKey: String,
    val expirationTime: Long,
)