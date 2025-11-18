package com.ecommerce.domain.account.exception

import com.ecommerce.domain.CoreException

class AccountIdNegativeException(
    cause: Throwable? = null,
): CoreException("계정 ID는 음수일 수 없습니다.", cause)