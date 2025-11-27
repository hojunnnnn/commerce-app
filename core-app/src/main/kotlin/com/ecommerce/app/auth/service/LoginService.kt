package com.ecommerce.app.auth.service

import com.ecommerce.app.account.service.AccountAccessor
import com.ecommerce.app.auth.port.`in`.LoginCommand
import com.ecommerce.app.auth.port.`in`.LoginResult
import com.ecommerce.app.auth.port.`in`.LoginUseCase
import com.ecommerce.app.auth.port.out.PasswordEncoder
import com.ecommerce.app.auth.port.out.TokenProvider
import com.ecommerce.app.auth.service.exception.PasswordMisMatchException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val accountAccessor: AccountAccessor,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
): LoginUseCase {

    @Transactional
    override fun login(command: LoginCommand): LoginResult {
        val account = accountAccessor.readByEmail(command.email)

        if (!passwordEncoder.matches(command.password, account.password.value)) {
            throw PasswordMisMatchException()
        }

        val token = tokenProvider.generateToken(account.id.value.toString())

        return LoginResult(
            id = account.id.value,
            email = account.email.value,
            name = account.name.value,
            accessToken = token,
        )
    }


}

