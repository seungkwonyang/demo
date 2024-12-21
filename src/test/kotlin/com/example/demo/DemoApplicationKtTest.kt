package com.example.demo

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test") // 테스트 프로파일을 사용
class DemoApplicationTests {

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun `should load application context with all beans`() {
        // 전체 애플리케이션 컨텍스트가 null이 아닌지 확인
        assertNotNull(context, "Application context should not be null")

    }
}