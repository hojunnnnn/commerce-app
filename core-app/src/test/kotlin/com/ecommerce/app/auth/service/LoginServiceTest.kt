package com.ecommerce.app.auth.service

import com.ecommerce.app.account.service.AccountAccessor
import com.ecommerce.app.auth.port.`in`.LoginCommand
import com.ecommerce.app.auth.port.out.PasswordEncoder
import com.ecommerce.app.auth.port.out.TokenProvider
import com.ecommerce.app.auth.service.exception.PasswordMisMatchException
import com.ecommerce.domain.account.Account
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class LoginServiceTest {

    val accountAccessor = mockk<AccountAccessor>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val tokenProvider = mockk<TokenProvider>()

    val loginService = LoginService(
        accountAccessor = accountAccessor,
        passwordEncoder = passwordEncoder,
        tokenProvider = tokenProvider
    )


    @Nested
    inner class `로그인` {
        val command = LoginCommand("test@ecommerce.com", "passwordpasswordpassword")
        val account = Account.create("test@ecommerce.com","password123", "testUser")

        @Test
        fun `비밀번호가 일치하지 않을 경우 예외가 발생한다`() {
            every { accountAccessor.readByEmail(any()) } returns account
            every { passwordEncoder.matches(any(), any()) } returns false

            assertThrows<PasswordMisMatchException> { loginService.login(command) }
            verify(exactly = 0) { tokenProvider.generateToken("test@ecommerce.com") }
        }

        @Test
        fun `성공하면 응답을 반환한다`() {
            val token = "thisistesttoken"
            every { accountAccessor.readByEmail(any()) } returns account
            every { passwordEncoder.matches(any(), any()) } returns true
            every { tokenProvider.generateToken(any()) } returns token

            val response = loginService.login(command)

            assertThat(response.accessToken).isEqualTo(token)
            assertThat(response.email).isEqualTo(account.email.value)
            assertThat(response.name).isEqualTo(account.name.value)

        }
    }


}