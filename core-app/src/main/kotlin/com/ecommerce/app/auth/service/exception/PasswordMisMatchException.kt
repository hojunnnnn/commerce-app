package com.ecommerce.app.auth.service.exception

import com.ecommerce.domain.CoreException

class PasswordMisMatchException(
    cause: Throwable? = null,
): CoreException("비밀번호가 일치하지 않습니다.", cause)