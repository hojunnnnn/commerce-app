package com.ecommerce.domain.account.vo

import com.ecommerce.domain.account.exception.AccountIdNegativeException

@JvmInline
value class AccountId(val value: Long) {

    init {
        if(value < 0) {
            throw AccountIdNegativeException()
        }
    }

    companion object {
        fun generate(value: Long = 0L): AccountId = AccountId(value)
    }
}