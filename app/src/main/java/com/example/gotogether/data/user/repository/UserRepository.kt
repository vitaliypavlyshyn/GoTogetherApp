package com.example.gotogether.data.user.repository

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.user.UpdateUserRequest
import com.example.gotogether.domain.user.DeleteUser
import com.example.gotogether.domain.user.User

interface UserRepository {
    suspend fun getUser(userUuid: String): Result<User>

    suspend fun getCurrentUser(): Result<User>

    suspend fun updateUser(userUuid: String, request: UpdateUserRequest): Result<ResponseDTO>

    suspend fun deleteUser(userUuid: String): Result<ResponseDTO>
}