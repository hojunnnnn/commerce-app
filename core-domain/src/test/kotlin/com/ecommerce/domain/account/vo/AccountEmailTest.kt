package com.ecommerce.domain.account.vo

import com.ecommerce.domain.account.exception.AccountInvalidEmailFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AccountEmailTest {

    @ParameterizedTest
    @ValueSource(strings = ["A1_B2@ecommerce.com", "test@ecommerce.com"])
    fun `계정 이메일은 이메일 형식이어야 한다`(email : String) {
        val accountEmail = AccountEmail(email)
        assertThat(accountEmail.value).isEqualTo(email)
    }

    @ParameterizedTest
    @ValueSource(strings = ["A1_B2ecommerceCom", "test...ecommerce@com"])
    fun `이메일 형식이 아니면 예외가 발생한다`(email : String) {
        assertThrows<AccountInvalidEmailFormatException> { AccountEmail(email) }
    }
}