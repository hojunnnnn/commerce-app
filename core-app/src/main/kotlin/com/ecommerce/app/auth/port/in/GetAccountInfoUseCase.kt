package com.ecommerce.app.auth.port.`in`

interface GetAccountInfoUseCase {

    fun getAccountInfo(accountId: Long): AccountInfo
}