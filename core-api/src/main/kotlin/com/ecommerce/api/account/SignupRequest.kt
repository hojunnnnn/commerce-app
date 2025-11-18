package com.ecommerce.api.account

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
)