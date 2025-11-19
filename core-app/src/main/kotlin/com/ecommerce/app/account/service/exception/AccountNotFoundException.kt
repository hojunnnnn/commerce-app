package com.ecommerce.app.account.service.exception

import com.ecommerce.domain.CoreException

class AccountNotFoundException(
    cause: Throwable? = null,
): CoreException("존재하지 않는 계정입니다.", cause)