package com.ecommerce.infra.jpa

import extensions.camelToSnakeCase
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseCleaner(
    @PersistenceContext
    private val em: EntityManager,
) {

    @Transactional
    fun clear() {
//        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate()
        truncateTables()
//        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate()
        em.clear()

    }

    private fun truncateTables() {
        val tables = em.metamodel.entities.map { it.name.camelToSnakeCase() }
        for (table in tables) {
            em.createNativeQuery("TRUNCATE table $table").executeUpdate()
        }
    }

    }