package com.ecommerce.domain

abstract class CoreException(
    message: String? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause)