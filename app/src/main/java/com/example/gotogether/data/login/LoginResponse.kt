package com.example.gotogether.data.login

import com.example.gotogether.domain.login.Login

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
)

fun LoginResponse.toDomain(): Login {
    return Login(
        success = success,
        message = message,
        token = token
    )
}

data class LoginRequest(
    val email: String,
    val password: String
)
