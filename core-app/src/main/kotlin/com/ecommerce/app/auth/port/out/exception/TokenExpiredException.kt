package com.ecommerce.app.auth.port.out.exception

class TokenExpiredException(
    cause: Throwable? = null,
): RuntimeException("만료된 토큰입니다.", cause) {
}