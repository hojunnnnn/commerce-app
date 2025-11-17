package com.ecommerce.domain.account.exception

import com.ecommerce.domain.DomainException

class AccountNameBlankException (
    cause: Throwable? = null,
): DomainException("계정 이름은 공백으로만 이루어질 수 없습니다.", cause)