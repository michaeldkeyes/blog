package com.keyes.blog.integration

import com.keyes.blog.TestWithTestContainer
import com.keyes.blog.dto.RegisterRequest
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
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests @Autowired constructor(
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
        val registerRequest = RegisterRequest("username", "password", "email")

        val response = restTemplate.postForEntity<Unit>("$baseUrl/signup", registerRequest)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        //assertThat(registerRequest).usingRecursiveComparison().ignoringFields("id").isEqualTo(registerRequest)
    }
}