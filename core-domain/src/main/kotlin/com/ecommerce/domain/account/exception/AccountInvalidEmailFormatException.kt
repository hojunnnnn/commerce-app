package com.ecommerce.domain.account.exception

import com.ecommerce.domain.DomainException

class AccountInvalidEmailFormatException(
    cause: Throwable? = null,
): DomainException("올바르지 않은 이메일 형식입니다", cause)