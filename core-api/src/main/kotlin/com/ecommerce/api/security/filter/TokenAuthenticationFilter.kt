package com.ecommerce.api.security.filter

import com.ecommerce.api.security.AuthenticationToken
import com.ecommerce.app.auth.port.`in`.AccountInfo
import com.ecommerce.app.auth.port.`in`.GetAccountInfoUseCase
import com.ecommerce.app.auth.port.out.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

class TokenAuthenticationFilter(
    private val tokenProvider: TokenProvider,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val allowListPatterns: Collection<String>,
): OncePerRequestFilter() {

    private val pathMatcher = AntPathMatcher()

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val requestUri = request.requestURI
        return allowListPatterns.any { pattern -> pathMatcher.match(pattern, requestUri) }
    }


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = AuthHeader.resolveToken(request)
        token?.let { handleToken(it) }

        return filterChain.doFilter(request, response)
    }

    private fun handleToken(token: String) {
        val principal = extractPrincipal(token)
        val accountInfo = fetchAccountInfo(principal)

        val authentication = AuthenticationToken.generate(
            id = accountInfo.id,
            email = accountInfo.email,
        )
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun extractPrincipal(token: String): Long {
        return tokenProvider.getPrincipal(token)
    }

    private fun fetchAccountInfo(principal: Long): AccountInfo {
        return getAccountInfoUseCase.getAccountInfo(principal)
    }
}

