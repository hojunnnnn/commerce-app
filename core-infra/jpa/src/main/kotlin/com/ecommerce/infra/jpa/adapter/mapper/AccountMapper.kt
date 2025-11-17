package com.ecommerce.infra.jpa.adapter.mapper

import com.ecommerce.domain.account.Account
import com.ecommerce.infra.jpa.entity.AccountEntity

object AccountMapper {

    fun toDomain(entity: AccountEntity): Account {
        return Account.reconstruct(
            id = entity.id,
            email = entity.email,
            password = entity.password,
            name = entity.name,
        )
    }

    fun toEntity(account: Account): AccountEntity {
        return AccountEntity(
            email = account.email.value,
            password = account.password.value,
            name = account.name.value,
        )
    }
}