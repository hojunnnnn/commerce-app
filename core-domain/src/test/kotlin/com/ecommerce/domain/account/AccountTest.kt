package com.ecommerce.domain.account

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AccountTest {

    @Test
    fun `계정을 생성할 수 있다`() {
        val email = "test@ecommerce.com"
        val password = "password123"
        val name = "testUser"

        val account = Account.create(email, password, name)

        assertThat(account.email.value).isEqualTo(email)
        assertThat(account.password.value).isEqualTo(password)
        assertThat(account.name.value).isEqualTo(name)
    }

}