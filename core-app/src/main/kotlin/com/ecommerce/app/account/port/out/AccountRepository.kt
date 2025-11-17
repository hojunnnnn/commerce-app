package com.ecommerce.app.account.port.out

import com.ecommerce.domain.account.Account

interface AccountRepository {

    fun save(account: Account): Account

    fun findById(id: Long): Account?

    fun existsByEmail(email: String): Boolean
}