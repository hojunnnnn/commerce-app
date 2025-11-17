package com.ecommerce.domain.account.vo

@JvmInline
value class AccountEmail(val value: String) {

    init {
        if(!EMAIL_REGEX.matches(value)) {
            throw IllegalArgumentException("올바르지 않은 이메일 형식입니다.")
        }
    }

    companion object {
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    }
}

