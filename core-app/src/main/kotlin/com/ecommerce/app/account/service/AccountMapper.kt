package com.ecommerce.app.account.service

import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.auth.port.`in`.AccountInfo
import com.ecommerce.domain.account.Account

object AccountMapper {

    fun toAccountInfo(account: Account): AccountInfo {
        return AccountInfo(
            id = account.id.value,
            email = account.email.value,
            name = account.name.value,
        )
    }

    fun toSignupResult(account: Account): SignupResult {
        return SignupResult(
            email = account.email.value,
            name = account.name.value,
        )
    }
}