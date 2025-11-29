package com.ecommerce.api.security

import com.ecommerce.api.security.filter.TokenAuthenticationEntryPoint
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
    private val authenticationEntryPoint: TokenAuthenticationEntryPoint,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val tokenAllowListPatterns = listOf(
            "/docs/**",
            "/h2-console/**",
            "/api/v1/accounts/signup",
            "/api/v1/auth/login",
        )
        val tokenAuthenticationFilter = TokenAuthenticationFilter(
            tokenProvider = tokenProvider,
            getAccountInfoUseCase = getAccountInfoUseCase,
            allowListPatterns = tokenAllowListPatterns,
        )

        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
            .headers { header -> header.frameOptions { it.sameOrigin() } }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(* tokenAllowListPatterns.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}