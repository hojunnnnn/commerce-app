package com.ecommerce.api.account

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignupRequest(
    @field:Email(message = "invalid email format")
    val email: String,
    @field:NotBlank(message = "password is required")
    val password: String,
    @field:NotBlank(message = "email is required")
    val name: String,
)