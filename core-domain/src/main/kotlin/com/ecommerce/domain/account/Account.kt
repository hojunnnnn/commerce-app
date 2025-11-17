package com.ecommerce.domain.account

data class Account(
    val id: Long,
    val email: String,
    val password: String,
    val name: String,
) {

    companion object {
        fun create(id: Long = 0L, email: String, password: String, name: String): Account {
            return Account(
                id = id,
                email = email,
                password = password,
                name = name
            )
        }
    }
}