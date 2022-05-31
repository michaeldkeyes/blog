package com.keyes.blog

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

fun postgres(imageName: String, opts: JdbcDatabaseContainer<Nothing>.() -> Unit) = PostgreSQLContainer<Nothing>(
    DockerImageName.parse(imageName)
).apply(opts)

@Testcontainers
open class TestWithTestContainer {

    companion object {
        @Container
        val container = postgres("postgres:alpine") {
            withDatabaseName("db")
            withUsername("postgres")
            withPassword("password")
            //withInitScript("sql/schema.sql")
        }

        @JvmStatic
        @DynamicPropertySource
        fun datasourceConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
        }
    }
}