package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.auth.port.out.PasswordEncoder
import com.ecommerce.domain.account.Account
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AccountRegistrationServiceTest {

    val accountRepository = mockk<AccountRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()

    val accountRegistrationService = AccountRegistrationService(accountRepository, passwordEncoder)


    @Test
    fun `계정을 등록할 수 있다`() {
        val email = "test@test.com"
        val name = "testUser"
        val rawPassword = "password123"
        val encodedPassword = "encodedPassword123"
        val account = Account.create(email = email, name = name, password = rawPassword)

        every { accountRepository.existsByEmail(email) } returns false
        every { passwordEncoder.encode(rawPassword) } returns encodedPassword
        every { accountRepository.save(any())} returns account

        val result = accountRegistrationService.registerAccount(email, rawPassword, name)

        assertThat(result.email).isEqualTo(account.email)
        assertThat(result.name).isEqualTo(account.name)
        assertThat(result.password).isEqualTo(account.password)
    }

    @Test
    fun `이미 존재하는 계정 이메일일 경우 계정을 등록할 수 없다`() {
        val email = "test@test.com"
        val name = "testUser"
        val rawPassword = "password123"
        every { accountRepository.existsByEmail(email) } returns true

        val exception = assertThrows<IllegalArgumentException> {
            accountRegistrationService.registerAccount(email, rawPassword, name)
        }

        assertThat(exception.message).isEqualTo("이미 존재하는 계정 이메일 입니다.")
        verify(exactly = 0) { passwordEncoder.encode(rawPassword) }
        verify(exactly = 0) { accountRepository.save(any()) }
    }

}