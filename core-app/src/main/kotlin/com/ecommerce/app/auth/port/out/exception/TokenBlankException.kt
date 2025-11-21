package com.ecommerce.app.auth.port.out.exception

class TokenBlankException(
    cause: Throwable? = null,
): RuntimeException("토큰이 비어 있습니다.", cause)