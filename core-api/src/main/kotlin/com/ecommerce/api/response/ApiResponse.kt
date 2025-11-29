package com.ecommerce.api.response

import com.ecommerce.api.errors.ErrorType
import java.time.Instant

data class ApiResponse<T> private constructor(
    val result: ResultType,
    val status: Int,
    val data: T? = null,
    val error: ErrorMessage? = null,
    val timestamp: Instant = Instant.now(),
) {

    companion object {
        fun success(): ApiResponse<Any> = ApiResponse(ResultType.SUCCESS, 200, null, null)

        fun <T> success(data: T): ApiResponse<T> = ApiResponse(ResultType.SUCCESS, 200, data, null)

        fun error(errorType: ErrorType, errorData: Any? = null): ApiResponse<Unit> =
            ApiResponse(ResultType.ERROR, errorType.status.value(), null, ErrorMessage(errorType, errorData))
    }
}