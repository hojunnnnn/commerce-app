package com.ecommerce.domain.account.vo

@JvmInline
value class AccountId(val value: Long) {

    init {
        if(value < 0) {
            throw IllegalArgumentException("계정 ID는 음수일 수 없습니다.")

        }
    }

    companion object {
        fun generate(value: Long = 0L): AccountId = AccountId(value)
    }
}