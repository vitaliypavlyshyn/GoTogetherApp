package com.example.gotogether.data.login.repository

import com.example.gotogether.domain.login.Login

interface LoginRepository {
    suspend fun login(email: String, password: String): Login
}