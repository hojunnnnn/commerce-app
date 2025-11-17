package com.ecommerce.domain.account.vo

import com.ecommerce.domain.account.exception.AccountNameBlankException

@JvmInline
value class AccountName(val value: String) {

    init {
        if (value.isBlank()) {
            throw AccountNameBlankException()
        }
    }
}