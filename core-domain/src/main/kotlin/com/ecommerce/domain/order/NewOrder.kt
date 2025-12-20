package com.ecommerce.domain.order

import com.ecommerce.domain.account.vo.AccountId

data class NewOrder(
    val accountId: AccountId,
    val items: List<NewOrderItem>,
)