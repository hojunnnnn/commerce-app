package com.ecommerce.domain.account.exception

import com.ecommerce.domain.CoreException

class AccountNameBlankException (
    cause: Throwable? = null,
): CoreException("계정 이름은 공백으로만 이루어질 수 없습니다.", cause)