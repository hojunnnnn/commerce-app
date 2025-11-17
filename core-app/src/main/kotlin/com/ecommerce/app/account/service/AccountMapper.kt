package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.domain.account.Account

object AccountMapper {

    fun toSignupResult(account: Account): SignupResult {
        return SignupResult(
            email = account.email.value,
            name = account.name.value,
        )
    }
}