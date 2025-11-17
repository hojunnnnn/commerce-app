package com.ecommerce.domain.account.vo

@JvmInline
value class AccountName(val value: String) {

    init {
        if (value.isBlank()) {
            throw IllegalArgumentException("계정 이름은 공백으로만 이루어질 수 없습니다.")
        }
    }
}