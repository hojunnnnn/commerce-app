package com.ecommerce.domain.account.vo

@JvmInline
value class AccountPassword(val value: String) {

    init {
        if(value.length !in MIN_LENGTH..MAX_LENGTH) {
            throw IllegalArgumentException("계정 비밀번호는 ${MIN_LENGTH} ~ ${MAX_LENGTH}자 이내로 작성되어야 합니다.")
        }
    }

    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 100
    }
}