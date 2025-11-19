package com.ecommerce.api.account

import com.ecommerce.api.response.ApiResponse
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.account.port.`in`.SignupUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val signupUseCase: SignupUseCase,
) {

    @PostMapping("/api/v1/accounts/signup")
    fun signup(@RequestBody req: SignupRequest): ApiResponse<SignupResult> {
        val command = req.toCommand()
        return ApiResponse.success(signupUseCase.signup(command))
    }

}