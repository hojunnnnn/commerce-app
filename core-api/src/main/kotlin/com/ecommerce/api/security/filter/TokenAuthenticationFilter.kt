package com.ecommerce.api.security.filter

import com.ecommerce.api.errors.ErrorType
import com.ecommerce.api.security.AuthenticationToken
import com.ecommerce.app.account.service.exception.AccountNotFoundException
import com.ecommerce.app.auth.port.`in`.AccountInfo
import com.ecommerce.app.auth.port.`in`.GetAccountInfoUseCase
import com.ecommerce.app.auth.port.out.TokenProvider
import com.ecommerce.app.auth.port.out.exception.InvalidTokenException
import com.ecommerce.app.auth.port.out.exception.TokenBlankException
import com.ecommerce.app.auth.port.out.exception.TokenExpiredException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

class TokenAuthenticationFilter(
    private val tokenProvider: TokenProvider,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val allowListPatterns: Collection<String>,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
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
        try {
            val token = AuthHeader.resolveToken(request)
            token?.let { handleToken(it) }

            return filterChain.doFilter(request, response)
        } catch (e: AuthenticationException) {
            SecurityContextHolder.clearContext()
            authenticationEntryPoint.commence(request, response, e)
        }
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
        try {
            return tokenProvider.getPrincipal(token)
        } catch(e: TokenBlankException) {
            throw BadCredentialsException("토큰이 비어있습니다.", e)
        } catch(e: TokenExpiredException) {
            throw BadCredentialsException("토큰이 만료되었습니다.", e)
        } catch(e: InvalidTokenException) {
            throw BadCredentialsException("유효하지 않은 토큰입니다.", e)
        }

    }

    private fun fetchAccountInfo(principal: Long): AccountInfo {
        try {
            return getAccountInfoUseCase.getAccountInfo(principal)
        } catch (e: AccountNotFoundException) {
            throw BadCredentialsException("존재하지 않는 계정입니다.", e)
        }

    }
}

