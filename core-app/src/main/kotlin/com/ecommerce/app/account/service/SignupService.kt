package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.`in`.SignupCommand
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.account.port.`in`.SignupUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignupService(
    private val accountRegistrationService: AccountRegistrationService,
): SignupUseCase {

    @Transactional
    override fun signup(command: SignupCommand): SignupResult {
        val account = accountRegistrationService.registerAccount(
            email = command.email,
            password = command.password,
            name = command.name,
        )

        return AccountMapper.toSignupResult(account)
    }
}