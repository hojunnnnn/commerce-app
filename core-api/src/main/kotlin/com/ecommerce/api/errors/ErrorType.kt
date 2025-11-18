package com.ecommerce.api.errors

import org.springframework.http.HttpStatus

sealed interface ErrorType {
    val status: HttpStatus
    val message: String

    enum class Base(
        override val status: HttpStatus,
        override val message: String
    ): ErrorType {
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내 오류가 발생했습니다."),
        INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),


    }

    enum class Account(
        override val status: HttpStatus,
        override val message: String
    ): ErrorType {
        EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다."),
        INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다."),


    }

}