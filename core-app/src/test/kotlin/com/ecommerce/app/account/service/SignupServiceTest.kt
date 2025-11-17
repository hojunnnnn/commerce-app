package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.`in`.SignupCommand
import com.ecommerce.domain.account.Account
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SignupServiceTest {

    val accountRegistrationService = mockk<AccountRegistrationService>()
    val signupService = SignupService(accountRegistrationService)

    @Test
    fun `회원가입시 계정을 생성하고 계정 정보를 반환한다`() {
        val command = SignupCommand(
            email = "email@email.com",
            password = "password",
            name = "name"
        )
        val account = Account.create(
            email = command.email,
            password = command.password,
            name = command.name
        )
        every {
            accountRegistrationService.registerAccount(
                command.email,
                command.password,
                command.name
            )
        } returns account

        val result = signupService.signup(command)

        assertThat(result.email).isEqualTo(account.email)
        assertThat(result.name).isEqualTo(account.name)

        verify(exactly = 1) {
            accountRegistrationService.registerAccount(
                command.email,
                command.password,
                command.name
            )
        }
    }
}