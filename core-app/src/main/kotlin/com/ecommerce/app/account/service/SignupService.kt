package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.`in`.SignupCommand
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.account.port.`in`.SignupUseCase
import org.springframework.stereotype.Service

@Service
class SignupService(
    private val accountRegistrationService: AccountRegistrationService,
): SignupUseCase {

    override fun signup(command: SignupCommand): SignupResult {
        val account = accountRegistrationService.registerAccount(
            email = command.email,
            password = command.password,
            name = command.name,
        )

        return AccountMapper.toSignupResult(account)
    }
}