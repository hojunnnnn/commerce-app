package com.ecommerce.infra.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan("com.ecommerce.infra.jpa")
@EnableJpaRepositories("com.ecommerce.infra.jpa")
class JpaConfig