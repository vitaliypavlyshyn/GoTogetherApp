package com.example.gotogether.data.login

import com.example.gotogether.domain.login.Login

data class LoginResponseDTO(
    val success: Boolean,
    val message: String,
    val token: String? = null,
)

fun LoginResponseDTO.toDomain(): Login {
    return Login(
        success = success,
        message = message,
        token = token
    )
}