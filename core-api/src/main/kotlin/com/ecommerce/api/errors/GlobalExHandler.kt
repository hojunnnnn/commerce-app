package com.ecommerce.api.errors

import com.ecommerce.api.response.ApiResponse
import com.ecommerce.app.account.service.exception.AccountEmailExistsException
import com.ecommerce.app.account.service.exception.AccountNotFoundException
import com.ecommerce.app.auth.service.exception.PasswordMisMatchException
import com.ecommerce.app.product.service.exception.ProductMismatchInOrderException
import com.ecommerce.app.product.service.exception.ProductNotFoundException
import com.ecommerce.domain.CoreException
import com.ecommerce.domain.account.exception.AccountIdNegativeException
import com.ecommerce.domain.account.exception.AccountInvalidEmailFormatException
import com.ecommerce.domain.account.exception.AccountInvalidPasswordLengthException
import com.ecommerce.domain.account.exception.AccountNameBlankException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(CoreException::class)
    fun handle(ex: CoreException, request: HttpServletRequest): ResponseEntity<ApiResponse<Unit>> {
        logWarn(request, ex)
        val errorType = when (ex) {
            // Auth
            is PasswordMisMatchException -> ErrorType.Auth.PASSWORD_MISMATCH

            // Account
            is AccountEmailExistsException ->ErrorType.Account.EMAIL_ALREADY_EXISTS
            is AccountNotFoundException -> ErrorType.Account.NOT_FOUND
            is AccountIdNegativeException,
            is AccountInvalidEmailFormatException,
            is AccountInvalidPasswordLengthException,
            is AccountNameBlankException
                -> ErrorType.Account.INVALID_INPUT_VALUE

            // Product
            is ProductNotFoundException -> ErrorType.Product.PRODUCT_NOT_FOUND
            is ProductMismatchInOrderException -> ErrorType.Product.PRODUCT_MISMATCH_IN_ORDER

            else -> ErrorType.Base.INTERNAL_SERVER_ERROR
        }
        return ResponseEntity.status(errorType.status).body(ApiResponse.error(errorType))
    }

    private fun logWarn(
        request: HttpServletRequest,
        ex: Exception
    ) {
        logger.warn(
            """
                [🟠WARN] - [${request.method}] ${request.requestURI}
                message : ${ex.message}
            """
        )
    }

}