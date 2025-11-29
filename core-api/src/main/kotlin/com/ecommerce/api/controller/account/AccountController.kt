package com.ecommerce.api.controller.account

import com.ecommerce.api.response.ApiResponse
import com.ecommerce.api.security.AccountPrincipal
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.account.port.`in`.SignupUseCase
import com.ecommerce.app.auth.port.`in`.AccountInfo
import com.ecommerce.app.auth.port.`in`.GetAccountInfoUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val signupUseCase: SignupUseCase,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
) {

    @PostMapping("/api/v1/accounts/signup")
    fun signup(@RequestBody req: SignupRequest): ApiResponse<SignupResult> {
        val command = req.toCommand()
        return ApiResponse.success(signupUseCase.signup(command))
    }

    @GetMapping("/api/v1/accounts/me")
    fun me(
        @AuthenticationPrincipal accountPrincipal: AccountPrincipal,
    ): ApiResponse<AccountInfo> {
        return ApiResponse.success(getAccountInfoUseCase.getAccountInfo(accountPrincipal.id))
    }
}