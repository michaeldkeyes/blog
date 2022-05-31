package com.keyes.blog.service

import com.keyes.blog.dto.RegisterRequest
import com.keyes.blog.model.User
import com.keyes.blog.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(private val userRepository: UserRepository) {

    fun signup(registerRequest: RegisterRequest) {
        val user: User = User(
            null,
            registerRequest.username,
            registerRequest.password,
            registerRequest.email
        )

        userRepository.save(user)
    }
}