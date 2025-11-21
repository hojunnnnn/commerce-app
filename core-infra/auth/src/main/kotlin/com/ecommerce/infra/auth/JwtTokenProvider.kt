package com.ecommerce.infra.auth

import com.ecommerce.app.auth.port.out.TokenProvider
import com.ecommerce.app.auth.port.out.exception.InvalidTokenException
import com.ecommerce.app.auth.port.out.exception.TokenBlankException
import com.ecommerce.app.auth.port.out.exception.TokenExpiredException
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*

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

    override fun getPrincipal(token: String): Long {
        val claims = toClaims(token)

        val principal = claims.subject
        return principal.toLong()
    }

    private fun toClaims(token: String): Claims {
        if(token.isBlank()) throw TokenBlankException()

        try {
            val claimsJws: Jws<Claims> = getClaimsJws(token)
            return claimsJws.payload
        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException(e)
        } catch(e: JwtException) {
            throw InvalidTokenException(e)
        }

    }

    private fun getClaimsJws(token: String): Jws<Claims> {
        return Jwts.parser()
            .verifyWith(tokenSecretKey)
            .build()
            .parseSignedClaims(token)
    }

}