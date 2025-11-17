package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.app.account.service.exception.AccountEmailExistsException
import com.ecommerce.app.auth.port.out.PasswordEncoder
import com.ecommerce.domain.account.Account
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountRegistrationService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun registerAccount(email: String, password: String, name: String): Account {
        validateUniqueEmail(email)

        val encodedPw = passwordEncoder.encode(password)
        val account = Account.create(
            email = email,
            password = encodedPw,
            name = name,
        )
        return accountRepository.save(account)
    }

    private fun validateUniqueEmail(email: String) {
        if (accountRepository.existsByEmail(email)) {
            throw AccountEmailExistsException()
        }
    }
}