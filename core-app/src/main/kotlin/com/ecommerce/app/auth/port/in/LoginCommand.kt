package com.ecommerce.app.auth.port.`in`

data class LoginCommand(
    val email: String,
    val password: String,
)