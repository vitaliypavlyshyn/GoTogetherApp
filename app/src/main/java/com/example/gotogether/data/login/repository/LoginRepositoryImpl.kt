package com.example.gotogether.data.login.repository

import android.content.SharedPreferences
import com.example.gotogether.data.login.LoginApiService
import com.example.gotogether.data.login.LoginRequestDTO
import com.example.gotogether.data.login.LoginResponseDTO
import com.example.gotogether.data.login.toDomain
import com.example.gotogether.domain.login.Login
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepositoryImpl  @Inject constructor(
    private val api: LoginApiService,
    private val retrofit: Retrofit,
    private val sharedPreferences: SharedPreferences
) : LoginRepository {
    override suspend fun login(email: String, password: String): Login {
        val response = api.login(LoginRequestDTO(email, password))

        val errorConverter: Converter<ResponseBody, LoginResponseDTO> =
            retrofit.responseBodyConverter(LoginResponseDTO::class.java, arrayOf())

        val dto: LoginResponseDTO = when {
            response.isSuccessful -> {
                response.body()!!
            }
            else -> {
                response.errorBody()?.let { errBody ->
                    try {
                        errorConverter.convert(errBody)
                    } catch (_: Exception) {
                        LoginResponseDTO(
                            success = false,
                            message = "Server error ${response.code()}",
                            token = null
                        )
                    }
                } ?: LoginResponseDTO(
                    success = false,
                    message = "Unknown error ${response.code()}",
                    token = null
                )
            }
        }

        if (dto.success && !dto.token.isNullOrBlank()) {
            sharedPreferences.edit()
                .putString("auth_token", dto.token)
                .apply()
        }

        return dto.toDomain()
    }
}