package com.ecommerce.api.auth

import com.ecommerce.api.response.ApiResponse
import com.ecommerce.app.auth.port.`in`.LoginResult
import com.ecommerce.app.auth.port.`in`.LoginUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController(
    private val loginUseCase: LoginUseCase,
) {

    @PostMapping("/api/v1/auth/login")
    fun login(@RequestBody req: LoginRequest): ApiResponse<LoginResult> {
        val command = req.toCommand()
        return ApiResponse.success(loginUseCase.login(command))
    }
}