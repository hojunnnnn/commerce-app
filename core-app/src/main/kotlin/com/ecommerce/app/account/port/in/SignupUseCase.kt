package com.ecommerce.app.account.port.`in`

interface SignupUseCase {

    fun signup(command: SignupCommand): SignupResult

}