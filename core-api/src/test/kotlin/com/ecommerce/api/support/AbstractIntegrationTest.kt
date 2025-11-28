package com.ecommerce.api.support

import com.ecommerce.infra.jpa.DatabaseCleaner
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
abstract class AbstractIntegrationTest {

    @Autowired
    protected lateinit var databaseCleaner: DatabaseCleaner

    @AfterEach
    fun tearDown() {
        databaseCleaner.clear()
    }

}