package com.ecommerce.api.application.account

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.account.service.GetAccountInfoService
import com.ecommerce.domain.account.Account
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GetAccountInfoServiceIntegrationTest
@Autowired constructor(
    private val getAccountInfoService: GetAccountInfoService,
    private val accountRepository: AccountRepository,
) {

    @Test
    fun `계정 정보를 조회할 수 있다`() {
        // given
        val email = "test@ecommerce.com"
        val account = Account.create(
            email = email,
            password = "password",
            name = "테스트 계정"
        )
        val savedAccount = accountRepository.save(account)

        // when
        val accountInfo = getAccountInfoService.getAccountInfo(savedAccount.id.value)

        // then
        val foundAccount = accountRepository.findById(savedAccount.id.value)
        Assertions.assertThat(foundAccount).isNotNull()
        Assertions.assertThat(accountInfo.email).isEqualTo(email)
        Assertions.assertThat(foundAccount!!.email.value).isEqualTo(email)
    }

}