package com.keyes.blog.service

import com.keyes.blog.dto.RegisterRequest
import com.keyes.blog.model.User
import com.keyes.blog.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AuthServiceTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var authService: AuthService

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call repository to save user`() {
        val registerRequest = RegisterRequest("username", "password", "email")
        val user = User(null, registerRequest.username, registerRequest.password, registerRequest.email)
        every { userRepository.save(user) } returns user

        authService.signup(registerRequest)

        verify(exactly = 1) { userRepository.save(user) }
    }
}