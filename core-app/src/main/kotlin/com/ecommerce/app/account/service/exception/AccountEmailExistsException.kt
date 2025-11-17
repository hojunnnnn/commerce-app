package com.ecommerce.app.account.service.exception

import com.ecommerce.domain.DomainException

class AccountEmailExistsException(
    cause: Throwable? = null,
): DomainException("이미 존재하는 계정 이메일 입니다.", cause)