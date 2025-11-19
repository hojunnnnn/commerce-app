package com.ecommerce.infra.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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

}