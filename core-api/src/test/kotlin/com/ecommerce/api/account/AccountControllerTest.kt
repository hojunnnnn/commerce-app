package com.ecommerce.api.account

import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractWebMvcTest
import com.ecommerce.api.support.WithMockAccount
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.account.service.exception.AccountEmailExistsException
import com.ecommerce.app.auth.port.`in`.AccountInfo
import com.ecommerce.domain.account.exception.AccountInvalidEmailFormatException
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceDocumentation.headerWithName
import com.epages.restdocs.apispec.ResourceDocumentation.resource
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.epages.restdocs.apispec.Schema
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(RestDocumentationExtension::class)
class AccountControllerTest : AbstractWebMvcTest() {

    @BeforeEach
    fun setup(context: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

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

        result.andDo(
            MockMvcRestDocumentationWrapper.document(
                "내 계정 정보 조회 - 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Account")
                        .summary("내 계정 정보 조회")
                        .description("내 계정 정보 조회 API")
                        .requestHeaders(
                            headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰 Bearer {access-token}")
                        )
                        .responseSchema(Schema("ApiResponse<AccountInfo>"))
                        .responseFields(
                            *listOf(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("요청 결과 (SUCCESS/ERROR)"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("오류 정보"),
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 생성 시간"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("계정 고유 아이디"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("계정 이메일"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("계정 이름")
                            ).toTypedArray()
                        )
                        .build()
                )
            )
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

        val resultActions =
            mockMvc.post("/api/v1/accounts/signup") {
                contentType = MediaType.APPLICATION_JSON
                content = gson.toJson(request)
            }

        resultActions.andExpect {
            status { isBadRequest() }
            jsonPath("$.result") { value(ResultType.ERROR.name) }
            jsonPath("$.status") { value(400) }
            jsonPath("$.data") { value(null) }
            jsonPath("$.error.message") { value("입력 값이 유효하지 않습니다.") }
        }
    }

    @Test
    fun `회원가입 실패 - 이미 사용 중인 이메일`() {
        val request = SignupRequest(
            email = "existsEmail@ecommerce.com",
            password = "password1234",
            name = "testUser"
        )

        every { signupUseCase.signup(any()) } throws AccountEmailExistsException()

        val resultActions =
            mockMvc.post("/api/v1/accounts/signup") {
                contentType = MediaType.APPLICATION_JSON
                content = gson.toJson(request)
            }

        resultActions.andExpect {
            status { isBadRequest() }
            jsonPath("$.result") { value(ResultType.ERROR.name) }
            jsonPath("$.status") { value(400) }
            jsonPath("$.data") { value(null) }
            jsonPath("$.error.message") { value("이미 사용 중인 이메일입니다.") }
        }

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

        val resultActions =
            mockMvc.post("/api/v1/accounts/signup") {
                contentType = MediaType.APPLICATION_JSON
                content = gson.toJson(request)
            }

        resultActions.andExpect {
            status { isOk() }
            jsonPath("$.result") { value(ResultType.SUCCESS.name) }
            jsonPath("$.status") { value(200) }
            jsonPath("$.data.email") { value(request.email) }
            jsonPath("$.data.name") { value(request.name) }
            jsonPath("$.error") { value(null) }
        }
    }

}


