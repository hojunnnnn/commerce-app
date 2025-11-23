package com.ecommerce.api.support

import org.springframework.security.test.context.support.WithSecurityContext

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockAccountSecurityContextFactory::class)
annotation class WithMockAccount(
    val id: Long = 1L,
    val email: String = "mock@ecommerce.com",
    val name: String = "mockUser"
)
