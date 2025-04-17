package com.example.gotogether.domain.login

data class Login(
    val success: Boolean,
    val message: String,
    val token: String? = null
)
