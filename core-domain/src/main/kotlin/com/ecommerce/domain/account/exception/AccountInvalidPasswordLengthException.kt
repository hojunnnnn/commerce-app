package com.ecommerce.domain.account.exception

import com.ecommerce.domain.CoreException

class AccountInvalidPasswordLengthException(
    cause: Throwable? = null,
): CoreException("계정 비밀번호는 8자 이상 100자 이하로 작성되어야 합니다.", cause)