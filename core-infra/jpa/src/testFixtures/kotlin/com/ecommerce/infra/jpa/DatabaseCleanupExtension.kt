package com.ecommerce.infra.jpa

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class DatabaseCleanupExtension: AfterEachCallback {

    override fun afterEach(context: ExtensionContext) {
        val cleaner = SpringExtension.getApplicationContext(context)
            .getBean(DatabaseCleaner::class.java)
        cleaner.clear()
    }
}