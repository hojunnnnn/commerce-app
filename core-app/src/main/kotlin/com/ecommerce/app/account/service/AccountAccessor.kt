package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.account.service.exception.AccountNotFoundException
import org.springframework.stereotype.Component

@Component
class AccountAccessor(
    private val accountRepository: AccountRepository,
) {

    fun readByEmail(email: String) = accountRepository.findByEmail(email)
        ?: throw AccountNotFoundException()


}