package com.ecommerce.api.application.auth

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.account.service.AccountAccessor
import com.ecommerce.app.auth.port.`in`.LoginCommand
import com.ecommerce.app.auth.port.out.PasswordEncoder
import com.ecommerce.app.auth.port.out.TokenProvider
import com.ecommerce.app.auth.service.LoginService
import com.ecommerce.domain.account.Account
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LoginServiceIntegrationTest
@Autowired constructor(
    private val accountAccessor: AccountAccessor,
    private val tokenProvider: TokenProvider,
    private val accountRepository: AccountRepository,
) {

    class FakePasswordEncoder: PasswordEncoder {
        override fun encode(rawPassword: String): String = rawPassword
        override fun matches(rawPassword: String, encodedPassword: String): Boolean = true
    }

    val loginService = LoginService(
        accountAccessor = accountAccessor,
        tokenProvider = tokenProvider,
        passwordEncoder = FakePasswordEncoder(),
    )


    @Test
    fun `로그인 할 수 있다`() {
        // given
        val email = "test@ecommerce.com"
        val password = "password"
        val name = "테스트 계정"

        val account = Account.create(email, password, name)
        accountRepository.save(account)
        val command = LoginCommand(email, password)

        // when
        val result = loginService.login(command)

        // then
        assertThat(result.email).isEqualTo(email)
        assertThat(result.accessToken).isNotNull
    }

}