package com.ecommerce.domain.account.vo

import com.ecommerce.domain.account.exception.AccountIdNegativeException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AccountIdTest {

    @Test
    fun `계정 아이디는 양수여야 한다`() {
        val id = 10L
        val accountId = AccountId(id)

        assertThat(accountId.value).isEqualTo(id)
    }

    @Test
    fun `계정 아이디가 음수이면 예외가 발생한다`() {
        assertThrows<AccountIdNegativeException> { AccountId(-1) }
    }

}