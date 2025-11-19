package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.account.service.exception.AccountNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AccountAccessorTest {

    val accountRepository = mockk<AccountRepository>()
    val accountAccessor = AccountAccessor(accountRepository)

    @Test
    fun `이메일로 계정을 조회할 수 없으면 예외가 발생한다`() {
        every { accountRepository.findByEmail(any()) } returns null

        assertThrows<AccountNotFoundException> { accountAccessor.readByEmail("notExists@gmail.com") }
    }

}