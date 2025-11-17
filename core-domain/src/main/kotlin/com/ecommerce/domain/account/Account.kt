package com.ecommerce.domain.account

import com.ecommerce.domain.account.vo.AccountEmail
import com.ecommerce.domain.account.vo.AccountId
import com.ecommerce.domain.account.vo.AccountName
import com.ecommerce.domain.account.vo.AccountPassword

data class Account(
    val id: AccountId,
    val email: AccountEmail,
    val password: AccountPassword,
    val name: AccountName,
) {

    companion object {
        fun create(email: String, password: String, name: String): Account {
            return Account(
                id = AccountId.generate(),
                email = AccountEmail(email),
                password = AccountPassword(password),
                name = AccountName(name)
            )
        }

        fun reconstruct(
            id: Long,
            email: String,
            password: String,
            name: String,
        ): Account {
            return Account(
                id = AccountId(id),
                email = AccountEmail(email),
                password = AccountPassword(password),
                name = AccountName(name)
            )
        }
    }
}