package com.example.gotogether.data.user.repository

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.data.user.UpdateUserRequest
import com.example.gotogether.data.user.UserApiService
import com.example.gotogether.data.user.toDomain
import com.example.gotogether.domain.user.User
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(
    private val api: UserApiService,
) : UserRepository {
    override suspend fun getUser(userUuid: String): Result<User> {
        return try {
            val user = api.getUserByUuid(userUuid)
            Result.success(user.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val user = api.getCurrentUser()
            Result.success(user.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(userUuid: String, request: UpdateUserRequest): Result<ResponseDTO> {
        return try {
            val response = api.updateUser(userUuid, request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun deleteUser(userUuid: String): Result<ResponseDTO> {
        return try {
            val response = api.deleteUser(userUuid)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}