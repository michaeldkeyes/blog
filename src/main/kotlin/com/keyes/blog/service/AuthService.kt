package com.keyes.blog.service

import com.keyes.blog.dto.RegisterRequest
import com.keyes.blog.model.User
import com.keyes.blog.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun signup(registerRequest: RegisterRequest): Unit {
        val user: User = User(
            null,
            registerRequest.username,
            encodePassword(registerRequest.password),
            registerRequest.email
        )

        userRepository.save(user)
    }

    private fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }
}