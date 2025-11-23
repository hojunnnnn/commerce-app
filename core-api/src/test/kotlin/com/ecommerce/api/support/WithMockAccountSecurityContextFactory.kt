package com.ecommerce.api.support

import com.ecommerce.api.security.AuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockAccountSecurityContextFactory : WithSecurityContextFactory<WithMockAccount> {
    override fun createSecurityContext(annotation: WithMockAccount): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        val authentication = AuthenticationToken.generate(
            id = annotation.id,
            email = annotation.email,
        )
        context.authentication = authentication
        return context
    }
}