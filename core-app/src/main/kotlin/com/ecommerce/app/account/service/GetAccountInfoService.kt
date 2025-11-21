package com.ecommerce.app.account.service

import com.ecommerce.app.auth.port.`in`.AccountInfo
import com.ecommerce.app.auth.port.`in`.GetAccountInfoUseCase
import org.springframework.stereotype.Service

@Service
class GetAccountInfoService(
    private val accountAccessor: AccountAccessor,
): GetAccountInfoUseCase {
    override fun getAccountInfo(accountId: Long): AccountInfo {
        val account = accountAccessor.readById(accountId)
        return AccountMapper.toAccountInfo(account)
    }

}