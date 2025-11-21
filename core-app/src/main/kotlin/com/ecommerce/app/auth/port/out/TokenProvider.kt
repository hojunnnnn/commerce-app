package com.ecommerce.app.auth.port.out

interface TokenProvider {

    fun generateToken(principal: String): String

    fun getPrincipal(token: String): Long
}