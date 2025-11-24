package com.ecommerce.infra.auth

import com.ecommerce.app.auth.port.out.exception.InvalidTokenException
import com.ecommerce.app.auth.port.out.exception.TokenBlankException
import com.ecommerce.app.auth.port.out.exception.TokenExpiredException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class JwtTokenProviderTest {

    val secretKey = "thisistestsecretkeythisistestsecretkey"
    val expirationTime = 1000 * 60 * 60 * 24L // 1일
    val properties = JwtProperties(secretKey, expirationTime)
    val jwtTokenProvider = JwtTokenProvider(properties)

    @Test
    fun `유효한 Jwt 토큰을 생성할 수 있다`() {
        val principal = "owner"

        val token = jwtTokenProvider.generateToken(principal)

        val compact = Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .build()
            .parseSignedClaims(token)
            .payload

        assertThat(compact.subject).isEqualTo(principal)
        assertThat(compact.expiration.time - compact.issuedAt.time).isEqualTo(expirationTime)
    }

    @Test
    fun `유효한 토큰으로부터 accountId를 추출할 수 있다`() {
        val accountId = 123L
        val token = jwtTokenProvider.generateToken(accountId.toString())

        val extractedAccountId = jwtTokenProvider.getPrincipal(token)

        assertThat(extractedAccountId).isEqualTo(accountId)
    }

    @Test
    fun `만료된 토큰인 경우 예외가 발생한다`() {
        val expiredProperties = JwtProperties(
            secretKey = secretKey,
            expirationTime = 1L // 1ms
        )
        val expiredJwtTokenProvider = JwtTokenProvider(expiredProperties)

        val expiredToken = expiredJwtTokenProvider.generateToken("1L")
        Thread.sleep(100)

        assertThrows<TokenExpiredException> { jwtTokenProvider.getPrincipal(expiredToken) }
    }

    @Test
    fun `유효하지 않은 시크릿 키로 서명된 토큰인 경우 예외가 발생한다`() {
        val otherKey = "anothersecretkeyanothersecretkey"
        val jwtProperties = JwtProperties(
            secretKey = otherKey,
            expirationTime = expirationTime
        )
        val invalidSignedToken = JwtTokenProvider(jwtProperties).generateToken("1L")

        assertThrows<InvalidTokenException> { jwtTokenProvider.getPrincipal(invalidSignedToken) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "    "])
    fun `빈 토큰인 경우 예외가 발생한다`(token: String) {
        assertThrows<TokenBlankException> { jwtTokenProvider.getPrincipal(token) }
    }
}