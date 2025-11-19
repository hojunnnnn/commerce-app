package com.ecommerce.api.auth

import com.ecommerce.api.errors.ErrorType
import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractWebMvcTest
import com.ecommerce.app.account.service.exception.AccountNotFoundException
import com.ecommerce.app.auth.port.`in`.LoginResult
import com.ecommerce.app.auth.service.exception.PasswordMisMatchException
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class AuthControllerTest : AbstractWebMvcTest() {


    @Test
    fun `계정이 존재하지 않으면 NotFound를 반환한다`() {
        val req = LoginRequest(email = "test@ecommerce.com", password = "testpassword")

        every { loginUseCase.login(any()) } throws AccountNotFoundException()


        val resultActions = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = gson.toJson(req)
        }

        resultActions.andExpectAll {
            status { isNotFound() }
            jsonPath("$.result") { value(ResultType.ERROR.name) }
            jsonPath("$.status") { value(404) }
            jsonPath("$.data") { value(null) }
            jsonPath("$.error.message") { value(ErrorType.Account.NOT_FOUND.message) }
        }
    }

    @Test
    fun `비밀번호가 일치하지 않으면 BadRequest를 반환한다`() {
        val req = LoginRequest(email = "test@ecommerce.com", password = "testpassword")

        every { loginUseCase.login(any()) } throws PasswordMisMatchException()


        val resultActions = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = gson.toJson(req)
        }

        resultActions.andExpectAll {
            status { isBadRequest() }
            jsonPath("$.result") { value(ResultType.ERROR.name) }
            jsonPath("$.status") { value(400) }
            jsonPath("$.data") { value(null) }
            jsonPath("$.error.message") { value(ErrorType.Auth.PASSWORD_MISMATCH.message) }
        }

    }

    @Test
    fun `로그인 성공`() {
        val req = LoginRequest(email = "test@ecommerce.com", password = "testpassword")
        val result = LoginResult(
            id = 1L,
            email = req.email,
            name = "testUser",
            accessToken = "thisistesttoken"
        )
        every { loginUseCase.login(any()) } returns result


        val resultActions = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = gson.toJson(req)
        }

        resultActions.andExpectAll {
            status { isOk() }
            jsonPath("$.result") { value(ResultType.SUCCESS.name) }
            jsonPath("$.status") { value(200) }
            jsonPath("$.data.email") { value(result.email) }
            jsonPath("$.data.name") { value(result.name) }
            jsonPath("$.data.accessToken") { value(result.accessToken) }
            jsonPath("$.error") { value(null) }
        }

    }

}