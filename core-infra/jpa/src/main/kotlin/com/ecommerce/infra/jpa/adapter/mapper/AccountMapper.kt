package com.ecommerce.infra.jpa.adapter.mapper

import com.ecommerce.domain.account.Account
import com.ecommerce.infra.jpa.entity.AccountEntity

object AccountMapper {

    fun toDomain(entity: AccountEntity): Account {
        return Account.create(
            id = entity.id,
            email = entity.email,
            password = entity.password,
            name = entity.name,
        )
    }

    fun toEntity(account: Account): AccountEntity {
        return AccountEntity(
            email = account.email,
            password = account.password,
            name = account.name,
        )
    }
}