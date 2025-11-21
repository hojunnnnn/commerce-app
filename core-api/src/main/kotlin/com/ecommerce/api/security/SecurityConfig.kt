package com.ecommerce.api.security

import com.ecommerce.api.security.filter.TokenAuthenticationFilter
import com.ecommerce.app.auth.port.`in`.GetAccountInfoUseCase
import com.ecommerce.app.auth.port.out.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val tokenAuthenticationFilter = TokenAuthenticationFilter(
            tokenProvider = tokenProvider,
            getAccountInfoUseCase = getAccountInfoUseCase,
        )


        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(
                        "/",
                        "/health",
                        "/api/v1/accounts/signup",
                        "/api/v1/auth/login",
                        "/h2-console/**"
                    )
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .headers { header -> header.frameOptions { it.sameOrigin() } }
            .addFilterAt(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}