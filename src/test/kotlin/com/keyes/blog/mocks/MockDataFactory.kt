package com.keyes.blog.mocks

import com.keyes.blog.dto.RegisterRequest

internal class MockDataFactory {

    companion object Factory {
        fun createMockRegisterRequest() = RegisterRequest("username", "password", "email")
    }


}