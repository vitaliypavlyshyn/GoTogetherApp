package com.example.gotogether.data.user.repository

import com.example.gotogether.data.user.UserApiService
import com.example.gotogether.data.user.toDomain
import com.example.gotogether.domain.user.User
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(
    private val api: UserApiService
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
}