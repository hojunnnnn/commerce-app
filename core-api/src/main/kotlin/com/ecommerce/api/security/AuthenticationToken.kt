package com.ecommerce.api.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AuthenticationToken(
    private val accountPrincipal: AccountPrincipal,
    private val authorities: Collection<GrantedAuthority> = emptyList(),
): AbstractAuthenticationToken(authorities) {

    init {
        isAuthenticated = true
    }

    override fun getPrincipal(): AccountPrincipal = accountPrincipal

    override fun getCredentials(): Any? = null

    companion object {
        fun generate(
            id: Long,
            email: String,
            authorities: Collection<GrantedAuthority> = emptyList(),
        ): AuthenticationToken {
            return AuthenticationToken(
                accountPrincipal = AccountPrincipal(id, email),
                authorities = authorities,
            )
        }
    }

}