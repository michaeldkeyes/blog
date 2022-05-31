package com.keyes.blog.controller

import com.keyes.blog.dto.RegisterRequest
import com.keyes.blog.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    fun signup(@RequestBody registerRequest: RegisterRequest): ResponseEntity<Unit> {
        authService.signup(registerRequest)

        return ResponseEntity(HttpStatus.OK)
    }
}