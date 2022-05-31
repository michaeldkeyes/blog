package com.keyes.blog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.keyes.blog.dto.RegisterRequest
import com.keyes.blog.service.AuthService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
internal class AuthControllerTest @Autowired constructor(
    private var mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    @MockkBean(relaxed = true)
    private lateinit var authService: AuthService

    private val baseUrl = "/api/auth"

    @Test
    fun `should return OK`() {
        // If you want to use Spring Security
        //mockMvc = MockMvcBuilders.webAppContextSetup(context).apply { springSecurity() }.build()

        val registerRequest = RegisterRequest("username", "password", "email")
        every { authService.signup(registerRequest) } returns Unit

        mockMvc.post("$baseUrl/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(registerRequest)
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }
}