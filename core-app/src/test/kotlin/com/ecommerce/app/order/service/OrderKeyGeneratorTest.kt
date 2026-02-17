package com.ecommerce.app.order.service

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class OrderKeyGeneratorTest {

    @Test
    fun `현재 시간과 랜덤한 8자리 숫자를 조합하여 주문 번호를 생성할 수 있다`() {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val before = LocalDateTime.now().format(formatter)

        val result = OrderKeyGenerator().generate()

        val after = LocalDateTime.now().format(formatter)
        val prefix = result.substring(0, 8)

        println(result)
        assertThat(result.length).isEqualTo(16)
        assertThat(result).matches("^\\d{16}$")
        assertThat(result.substring(8)).matches("^\\d{8}$")
        assertThat(prefix == before || prefix == after).isTrue()
    }
}
