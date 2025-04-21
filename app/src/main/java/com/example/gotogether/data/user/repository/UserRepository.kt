package com.example.gotogether.data.user.repository

import com.example.gotogether.data.user.UpdateUserRequestDTO
import com.example.gotogether.domain.user.User

interface UserRepository {
    suspend fun getUser(userUuid: String): Result<User>

    suspend fun getCurrentUser(): Result<User>

    suspend fun updateUser(userUuid: String, request: UpdateUserRequestDTO): Result<String>
}