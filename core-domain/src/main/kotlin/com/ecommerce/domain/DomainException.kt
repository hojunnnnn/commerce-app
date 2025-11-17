package com.ecommerce.domain

abstract class DomainException(
    message: String? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause)