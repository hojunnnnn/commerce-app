package com.ecommerce.api.account

import com.ecommerce.api.response.ApiResponse
import com.ecommerce.app.account.port.`in`.SignupCommand
import com.ecommerce.app.account.port.`in`.SignupUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val signupUseCase: SignupUseCase,
) {

    @PostMapping("/api/v1/accounts/signup")
    fun signup(@RequestBody request: SignupRequest): ApiResponse<SignupResponse> {
        val command = SignupCommand.of(
            email = request.email,
            password = request.password,
            name = request.name,
        )
        val result = signupUseCase.signup(command)
        return ApiResponse.success(SignupResponse(result.email, result.name))
    }

}