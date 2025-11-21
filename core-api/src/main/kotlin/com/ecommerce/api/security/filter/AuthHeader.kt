package com.ecommerce.api.security.filter

import jakarta.servlet.http.HttpServletRequest

object AuthHeader {
    private const val AUTHORIZATION_HEADER = "authorization"
    private const val BEARER_PREFIX = "Bearer "

    fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader(AUTHORIZATION_HEADER)
        if(header.isNullOrBlank() || !header.startsWith(BEARER_PREFIX)) return null
        return header.removePrefix(BEARER_PREFIX).trim().ifBlank { null }
    }
}