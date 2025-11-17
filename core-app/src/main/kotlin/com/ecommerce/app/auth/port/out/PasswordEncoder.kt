package com.ecommerce.app.auth.port.out

interface PasswordEncoder {

    fun encode(rawPassword: String): String

    fun matches(rawPassword: String, encodedPassword: String): Boolean
}