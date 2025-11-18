package com.ecommerce.api.response

import com.ecommerce.api.errors.ErrorType

data class ErrorMessage private constructor(
    val message: String,
    val data: Any? = null
) {
    constructor(errorType: ErrorType, data: Any? = null) : this(
        message = errorType.message,
        data = data,
    )
}