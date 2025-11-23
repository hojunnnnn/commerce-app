package com.ecommerce.api.account

import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractWebMvcTest
import com.ecommerce.api.support.WithMockAccount
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.auth.port.`in`.AccountInfo
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class AccountControllerTest : AbstractWebMvcTest() {


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

        val resultActions = mockMvc.get("/api/v1/accounts/me") {
            contentType = MediaType.APPLICATION_JSON
            header(HttpHeaders.AUTHORIZATION, "Bearer $mockToken")
        }


        resultActions.andExpectAll {
            status { isOk() }
            jsonPath("$.result") { value(ResultType.SUCCESS.name) }
            jsonPath("$.status") { value(200) }
            jsonPath("$.data.id") { value(accountInfo.id) }
            jsonPath("$.data.email") { value(accountInfo.email) }
            jsonPath("$.data.name") { value(accountInfo.name) }
            jsonPath("$.error") { value(null) }
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


