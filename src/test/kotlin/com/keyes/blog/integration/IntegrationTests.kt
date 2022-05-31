package com.keyes.blog.integration

import com.keyes.blog.TestWithTestContainer
import com.keyes.blog.dto.RegisterRequest
import com.keyes.blog.mocks.MockDataFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class IntegrationTests @Autowired constructor(
    val restTemplate: TestRestTemplate,
    val jdbc: JdbcTemplate
) : TestWithTestContainer() {

    @AfterEach
    fun cleanup() {
        val tablesToTruncate = listOf("users", "post").joinToString()
        val sql = """
            TRUNCATE TABLE $tablesToTruncate
        """.trimIndent()
        jdbc.execute(sql)
    }

    val baseUrl = "/api/auth"

    @Test
    fun `container is up and running`() {
        assertTrue(container.isRunning)
    }

    @Test
    fun `should return 200 when user signs up`() {
        val registerRequest = MockDataFactory.createMockRegisterRequest()

        val response = restTemplate.postForEntity<Unit>("$baseUrl/signup", registerRequest)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        //assertThat(registerRequest).usingRecursiveComparison().ignoringFields("id").isEqualTo(registerRequest)
    }
}