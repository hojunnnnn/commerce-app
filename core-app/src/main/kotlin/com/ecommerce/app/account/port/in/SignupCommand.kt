package com.ecommerce.app.account.port.`in`

data class SignupCommand(
    val email: String,
    val password: String,
    val name: String,
) {
    companion object {
        fun of(email: String, password: String, name: String): SignupCommand {
            return SignupCommand(
                email = email,
                password = password,
                name = name
            )
        }
    }
}

