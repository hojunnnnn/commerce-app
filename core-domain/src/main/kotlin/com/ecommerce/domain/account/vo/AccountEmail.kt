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

@JvmInline
value class AccountName(val value: String) {

    init {
        if (value.isBlank()) {
            throw IllegalArgumentException("계정 이름은 공백으로만 이루어질 수 없습니다.")
        }
    }
}

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