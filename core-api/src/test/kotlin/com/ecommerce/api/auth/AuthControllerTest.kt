package com.ecommerce.api.auth

import com.ecommerce.api.errors.ErrorType
import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractRestDocumentTest
import com.ecommerce.app.account.service.exception.AccountNotFoundException
import com.ecommerce.app.auth.port.`in`.LoginResult
import com.ecommerce.app.auth.service.exception.PasswordMisMatchException
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.epages.restdocs.apispec.Schema
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthControllerTest : AbstractRestDocumentTest() {

    @Test
    fun `로그인 실패 - 존재하지 않는 계정`() {
        val req = LoginRequest(email = "test@ecommerce.com", password = "testpassword")

        every { loginUseCase.login(any()) } throws AccountNotFoundException()


        val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(req))
        )

        resultActions.andExpectAll(
            status().isNotFound,
            jsonPath("$.result").value(ResultType.ERROR.name),
            jsonPath("$.status").value(404),
            jsonPath("$.data").value(null),
            jsonPath("$.error.message").value(ErrorType.Account.NOT_FOUND.message)
        )


        resultActions.andDocument(
            "로그인 - 실패 - 존재하지 않는 계정",
            ResourceSnippetParameters.builder()
                .tag("Auth")
                .requestSchema(Schema("LoginRequest"))
                .requestFields(*loginRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<Unit>"))
                .responseFields(*commonFailureResponseFields().toTypedArray())
                .build()
        )

    }

    @Test
    fun `로그인 실패 - 비밀번호 불일치`() {
        val req = LoginRequest(email = "test@ecommerce.com", password = "testpassword")

        every { loginUseCase.login(any()) } throws PasswordMisMatchException()


        val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(req))
        )
        resultActions.andExpectAll(
            status().isBadRequest,
            jsonPath("$.result").value(ResultType.ERROR.name),
            jsonPath("$.status").value(400),
            jsonPath("$.data").value(null),
            jsonPath("$.error.message").value(ErrorType.Auth.PASSWORD_MISMATCH.message)
        )

        resultActions.andDocument(
            "로그인 - 실패 - 비밀번호 불일치",
            ResourceSnippetParameters.builder()
                .tag("Auth")
                .requestSchema(Schema("LoginRequest"))
                .requestFields(*loginRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<Unit>"))
                .responseFields(*commonFailureResponseFields().toTypedArray())
                .build()
        )

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

        val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(req))
        )

        resultActions.andExpectAll(
            status().isOk,
            jsonPath("$.result").value(ResultType.SUCCESS.name),
            jsonPath("$.status").value(200),
            jsonPath("$.data.email").value(result.email),
            jsonPath("$.data.name").value(result.name),
            jsonPath("$.data.accessToken").value(result.accessToken),
            jsonPath("$.error").value(null)
        )

        resultActions.andDocument(
            "로그인 - 성공",
            ResourceSnippetParameters.builder()
                .tag("Auth")
                .summary("로그인")
                .description("로그인 API")
                .requestSchema(Schema("LoginRequest"))
                .requestFields(*loginRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<LoginResult>"))
                .responseFields(*loginSuccessResponseFields().toTypedArray())
                .build()
        )
    }

    private fun loginRequestFields() = listOf(
        fieldWithPath("email").type(JsonFieldType.STRING).description("계정 이메일"),
        fieldWithPath("password").type(JsonFieldType.STRING).description("계정 비밀번호"),
    )

    private fun loginSuccessResponseFields() = commonSuccessResponseFields(
        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("계정 고유 아이디"),
        fieldWithPath("data.email").type(JsonFieldType.STRING).description("계정 이메일"),
        fieldWithPath("data.name").type(JsonFieldType.STRING).description("계정 이름"),
        fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("엑세스 토큰")
    )

}