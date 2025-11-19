package com.ecommerce.api.account

import com.ecommerce.app.account.port.`in`.SignupCommand

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
) {
    fun toCommand() = SignupCommand(
        email = email,
        password = password,
        name = name,
    )
}