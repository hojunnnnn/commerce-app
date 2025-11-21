package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.account.service.exception.AccountNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AccountAccessor(
    private val accountRepository: AccountRepository,
) {

    @Transactional(readOnly = true)
    fun readById(id: Long) = accountRepository.findById(id)
        ?: throw AccountNotFoundException()

    @Transactional(readOnly = true)
    fun readByEmail(email: String) = accountRepository.findByEmail(email)
        ?: throw AccountNotFoundException()

}