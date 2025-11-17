package com.ecommerce.api.account

import com.ecommerce.api.security.SecurityConfig
import com.ecommerce.app.account.port.`in`.SignupResult
import com.ecommerce.app.account.port.`in`.SignupUseCase
import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(AccountController::class)
@Import(SecurityConfig::class)
class AccountControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var gson: Gson

    @MockkBean
    private lateinit var signupUseCase: SignupUseCase

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
            jsonPath("$.email") { value(result.email) }
            jsonPath("$.name") { value(result.name) }
        }
    }

}


