package com.ecommerce.api.security.filter

import com.ecommerce.api.errors.ErrorType
import com.ecommerce.api.response.ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class TokenAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = Charsets.UTF_8.name()
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val errorResponse = ApiResponse.error(ErrorType.Security.UNAUTHORIZED)
        val body = objectMapper.writeValueAsString(errorResponse)

        response.writer.write(body)
    }
}