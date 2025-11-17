package com.ecommerce.domain.account.vo

import com.ecommerce.domain.account.exception.AccountInvalidPasswordLengthException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AccountPasswordTest {

    @ParameterizedTest
    @ValueSource(ints = [8, 100])
    fun `계정 비밀번호는 8자 이상 100자 이하 문자열이어야 한다`(count: Int) {
        val password = "a".repeat(count)
        val accountPassword = AccountPassword(password)

        assertThat(accountPassword.value).isEqualTo(password)
    }

    @ParameterizedTest
    @ValueSource(ints = [7, 101])
    fun `비밀번호가 8자 미만 또는 100자 초과인 경우 예외가 발생한다`(count: Int) {
        val password = "a".repeat(count)

        assertThrows<AccountInvalidPasswordLengthException> { AccountPassword(password) }
    }

}