package com.example.gotogether.data.user.repository

import com.example.gotogether.data.user.DeleteUserRequestDTO
import com.example.gotogether.data.user.DeleteUserResponseDTO
import com.example.gotogether.data.user.UpdateUserRequestDTO
import com.example.gotogether.domain.user.DeleteUser
import com.example.gotogether.domain.user.User
import retrofit2.Response

interface UserRepository {
    suspend fun getUser(userUuid: String): Result<User>

    suspend fun getCurrentUser(): Result<User>

    suspend fun updateUser(userUuid: String, request: UpdateUserRequestDTO): Result<String>

    suspend fun deleteUser(userUuid: String): DeleteUser
}