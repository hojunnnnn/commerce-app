package com.ecommerce.api.application.account

import com.ecommerce.api.support.AbstractIntegrationTest
import com.ecommerce.app.account.port.`in`.SignupCommand
import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.account.service.SignupService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SignupServiceIntegrationTest(
    private val signupService: SignupService,
    private val accountRepository: AccountRepository,
): AbstractIntegrationTest() {


    @Test
    fun `회원가입을 할 수 있다`() {
        // given
        val command = SignupCommand(
            email = "test@ecommerce.com",
            password = "password",
            name = "테스트 계정",
        )

        // when
        val result = signupService.signup(command)

        // then
        val savedAccount = accountRepository.findByEmail(result.email)
        assertThat(savedAccount).isNotNull
        assertThat(savedAccount!!.email.value).isEqualTo(command.email)
    }
}