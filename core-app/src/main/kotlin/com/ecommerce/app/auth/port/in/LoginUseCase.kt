package com.ecommerce.app.auth.port.`in`

interface LoginUseCase {

    fun login(command: LoginCommand): LoginResult

}

