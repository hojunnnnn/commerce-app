package com.ecommerce.infra.jpa.adapter

import com.ecommerce.app.account.port.out.AccountRepository
import com.ecommerce.domain.account.Account
import com.ecommerce.infra.jpa.adapter.mapper.AccountMapper
import com.ecommerce.infra.jpa.repository.AccountJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AccountAdapter(
    private val accountJpaRepository: AccountJpaRepository
): AccountRepository {

    override fun save(account: Account): Account {
        val entity = AccountMapper.toEntity(account)
        val savedEntity = accountJpaRepository.save(entity)
        return AccountMapper.toDomain(savedEntity)
    }

    override fun findById(id: Long): Account? {
        return accountJpaRepository.findByIdOrNull(id)?.let { AccountMapper.toDomain(it) }
    }

    override fun findByEmail(email: String): Account? {
        return accountJpaRepository.findByEmail(email)?.let { AccountMapper.toDomain(it) }
    }

    override fun existsByEmail(email: String): Boolean {
        return accountJpaRepository.existsByEmail(email)
    }
}