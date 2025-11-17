package com.ecommerce.api.account

import com.ecommerce.app.account.port.`in`.SignupCommand
import com.ecommerce.app.account.port.`in`.SignupUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val signupUseCase: SignupUseCase,
) {

    @PostMapping("/api/v1/accounts/signup")
    fun signup(@Valid @RequestBody request: SignupRequest): ResponseEntity<SignupResponse> {
        val command = SignupCommand.of(
            email = request.email,
            password = request.password,
            name = request.name,
        )
        val result = signupUseCase.signup(command)
        val response = SignupResponse(
            email = result.email,
            name = result.name,
        )
        return ResponseEntity.ok(response)
    }

}