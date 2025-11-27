package com.ecommerce.api.web.account

import com.ecommerce.api.account.SignupRequest
import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractRestDocumentTest
import com.ecommerce.api.support.WithMockAccount
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.account.service.exception.AccountEmailExistsException
import com.ecommerce.app.auth.port.`in`.AccountInfo
import com.ecommerce.domain.account.exception.AccountInvalidEmailFormatException
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.epages.restdocs.apispec.Schema
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccountControllerTest : AbstractRestDocumentTest() {

    @Test
    @WithMockAccount
    fun `내 계정 정보 조회 성공`() {
        val mockToken = "thisismocktoken"
        val accountInfo = AccountInfo(
            id = 1L,
            email = "test@ecommerce.com",
            name = "testUser",
        )

        every { getAccountInfoUseCase.getAccountInfo(any()) } returns accountInfo

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders
                .get("/api/v1/accounts/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $mockToken")
        )

        result.andExpectAll(
            status().isOk,
            jsonPath("$.result").value(ResultType.SUCCESS.name),
            jsonPath("$.status").value(200),
            jsonPath("$.data.id").value(accountInfo.id),
            jsonPath("$.data.email").value(accountInfo.email),
            jsonPath("$.data.name").value(accountInfo.name),
            jsonPath("$.error").value(null),
        )
        result.andDocument(
            "내 계정 정보 조회 - 성공",
            ResourceSnippetParameters.builder()
                .tag("Account")
                .summary("내 계정 정보 조회")
                .description("내 계정 정보 조회 API")
                .requestHeaders(authHeaderFields())
                .responseSchema(Schema("ApiResponse<AccountInfo>"))
                .responseFields(*getAccountInfoSuccessResponseFields().toTypedArray())
                .build()
        )

    }

    @Test
    fun `회원가입 실패 - 잘못된 입력값`() {
        val request = SignupRequest(
            email = "invalid-email-format",
            password = "password1234",
            name = "testUser"
        )

        every { signupUseCase.signup(any()) } throws AccountInvalidEmailFormatException()

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        )
        result.andExpectAll(
            status().isBadRequest(),
            jsonPath("$.result").value(ResultType.ERROR.name),
            jsonPath("$.status").value(400),
            jsonPath("$.data").value(null),
            jsonPath("$.error.message").value("입력 값이 유효하지 않습니다.")
        )

        result.andDocument(
            "회원가입 - 실패 - 잘못된 입력값",
            ResourceSnippetParameters.builder()
                .tag("Account")
                .requestSchema(Schema("SignupRequest"))
                .requestFields(*signupRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<Unit>"))
                .responseFields(*commonFailureResponseFields().toTypedArray())
                .build()
        )

    }

    @Test
    fun `회원가입 실패 - 이미 사용 중인 이메일`() {
        val request = SignupRequest(
            email = "existsEmail@ecommerce.com",
            password = "password1234",
            name = "testUser"
        )

        every { signupUseCase.signup(any()) } throws AccountEmailExistsException()

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        )

        result.andDocument(
            "회원가입 - 실패 - 이미 사용 중인 이메일",
            ResourceSnippetParameters.builder()
                .tag("Account")
                .requestSchema(Schema("SignupRequest"))
                .requestFields(*signupRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<Unit>"))
                .responseFields(*commonFailureResponseFields().toTypedArray())
                .build()
        )

    }


    @Test
    fun `회원가입 성공`() {
        val request = SignupRequest(
            email = "test@test.com",
            password = "test1234",
            name = "testUser"
        )
        val result = SignupResult(
            email = request.email,
            name = request.name,
        )

        every { signupUseCase.signup(any()) } returns result


        val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/v1/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        )

        resultActions.andExpectAll(
            status().isOk(),
            jsonPath("$.result").value(ResultType.SUCCESS.name),
            jsonPath("$.status").value(200),
            jsonPath("$.data.email").value(request.email),
            jsonPath("$.data.name").value(request.name),
            jsonPath("$.error").value(null),
        )

        resultActions.andDocument(
            "회원가입 - 성공",
            ResourceSnippetParameters.builder()
                .tag("Account")
                .summary("회원가입")
                .description("회원가입 API")
                .requestSchema(Schema("SignupRequest"))
                .requestFields(*signupRequestFields().toTypedArray())
                .responseSchema(Schema("ApiResponse<SignupResult>"))
                .responseFields(*signupSuccessResponseFields().toTypedArray())
                .build()
            )
    }

    private fun getAccountInfoSuccessResponseFields() = commonSuccessResponseFields(
        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("계정 고유 아이디"),
        fieldWithPath("data.email").type(JsonFieldType.STRING).description("계정 이메일"),
        fieldWithPath("data.name").type(JsonFieldType.STRING).description("계정 이름")
    )

    private fun signupRequestFields() = listOf(
        fieldWithPath("email").type(JsonFieldType.STRING).description("계정 이메일"),
        fieldWithPath("password").type(JsonFieldType.STRING).description("계정 비밀번호"),
        fieldWithPath("name").type(JsonFieldType.STRING).description("계정 이름")
    )

    private fun signupSuccessResponseFields() = commonSuccessResponseFields(
        fieldWithPath("data.email").type(JsonFieldType.STRING).description("계정 이메일"),
        fieldWithPath("data.name").type(JsonFieldType.STRING).description("계정 이름")
    )
}


