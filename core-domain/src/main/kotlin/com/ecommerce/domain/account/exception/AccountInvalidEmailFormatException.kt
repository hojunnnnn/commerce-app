package com.ecommerce.domain.account.exception

import com.ecommerce.domain.CoreException

class AccountInvalidEmailFormatException(
    cause: Throwable? = null,
): CoreException("올바르지 않은 이메일 형식입니다", cause)