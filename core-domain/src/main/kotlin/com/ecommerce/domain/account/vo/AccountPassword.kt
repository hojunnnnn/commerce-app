package com.ecommerce.domain.account.vo

import com.ecommerce.domain.account.exception.AccountInvalidPasswordLengthException

@JvmInline
value class AccountPassword(val value: String) {

    init {
        if(value.length !in MIN_LENGTH..MAX_LENGTH) {
            throw AccountInvalidPasswordLengthException()
        }
    }

    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 100
    }
}