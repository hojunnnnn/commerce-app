package com.ecommerce.api.account

import com.ecommerce.api.response.ResultType
import com.ecommerce.api.support.AbstractWebMvcTest
import com.ecommerce.app.account.port.`in`.SignupResult
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class AccountControllerTest : AbstractWebMvcTest() {


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


