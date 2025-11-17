package com.ecommerce.infra.jpa.repository

import com.ecommerce.infra.jpa.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountJpaRepository: JpaRepository<AccountEntity, Long> {

    fun existsByEmail(email: String): Boolean
}