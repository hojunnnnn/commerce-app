package com.ecommerce.infra.auth

import com.ecommerce.app.auth.port.out.TokenProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.util.Date

@Component
@EnableConfigurationProperties(JwtProperties::class)
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
): TokenProvider {

    private val tokenSecretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
    private val tokenExpirationTime = jwtProperties.expirationTime

    override fun generateToken(principal: String): String {
        val now = Date()
        val expiration = Date(now.time + tokenExpirationTime)

        return Jwts.builder()
            .subject(principal)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(tokenSecretKey)
            .compact()
    }

}