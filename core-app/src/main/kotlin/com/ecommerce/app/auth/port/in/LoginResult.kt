package com.ecommerce.app.auth.port.`in`

data class LoginResult(
    val id: Long,
    val email: String,
    val name: String,
    val accessToken: String,
)