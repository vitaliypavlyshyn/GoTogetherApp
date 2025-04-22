package com.example.gotogether.data.user.repository

import com.example.gotogether.data.login.LoginRequestDTO
import com.example.gotogether.data.login.LoginResponseDTO
import com.example.gotogether.data.login.toDomain
import com.example.gotogether.data.user.DeleteUserRequestDTO
import com.example.gotogether.data.user.DeleteUserResponseDTO
import com.example.gotogether.data.user.UpdateUserRequestDTO
import com.example.gotogether.data.user.UserApiService
import com.example.gotogether.data.user.toDomain
import com.example.gotogether.domain.user.DeleteUser
import com.example.gotogether.domain.user.User
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(
    private val api: UserApiService,
    private val retrofit: Retrofit
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

    override suspend fun updateUser(userUuid: String, request: UpdateUserRequestDTO): Result<String> {
        return try {
            val response = api.updateUser(userUuid, request)
            if (response.isSuccessful) {
                Result.success(response.body() ?: "Інформацію про користувача оновлено")
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(userUuid: String): DeleteUser {
        val response = api.deleteUser(DeleteUserRequestDTO(userUuid))

        val errorConverter: Converter<ResponseBody, DeleteUserResponseDTO> =
            retrofit.responseBodyConverter(DeleteUserResponseDTO::class.java, arrayOf())

        val dto: DeleteUserResponseDTO = when {
            response.isSuccessful -> {
                response.body()!!
            }
            else -> {
                response.errorBody()?.let { errBody ->
                    try {
                        errorConverter.convert(errBody)
                    } catch (_: Exception) {
                        DeleteUserResponseDTO(
                            success = false,
                            message = "Server error ${response.code()}",
                        )
                    }
                } ?: DeleteUserResponseDTO(
                    success = false,
                    message = "Unknown error ${response.code()}",
                )
            }
        }

        return dto.toDomain()
    }
}