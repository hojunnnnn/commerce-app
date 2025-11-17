package com.ecommerce.domain.account.vo

import com.ecommerce.domain.account.exception.AccountNameBlankException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AccountNameTest {

    @Test
    fun `계정 이름이 공백이면 예외가 발생한다`() {
        assertThrows<AccountNameBlankException> { AccountName("   ") }
    }

}