package com.ecommerce.api.auth

import com.ecommerce.app.auth.port.`in`.LoginCommand

class LoginRequest(
    val email: String,
    val password: String,
) {
    fun toCommand(): LoginCommand {
        return LoginCommand(
            email = this.email,
            password = this.password,
        )
    }
}