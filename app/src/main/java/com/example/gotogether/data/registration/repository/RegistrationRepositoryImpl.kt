package com.example.gotogether.data.registration.repository

import com.example.gotogether.data.registration.RegistrationApiService
import com.example.gotogether.data.registration.RegistrationDTO
import com.example.gotogether.data.registration.RegistrationResponseDTO
import com.example.gotogether.data.registration.toDomain
import com.example.gotogether.domain.registration.Registration
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationApiService,
    private val retrofit: Retrofit,
) : RegistrationRepository {
    override suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
    ): Registration {
        val response = api.register(
            RegistrationDTO(
                firstName = firstName,
                lastName = lastName,
                dateOfBirth = dateOfBirth,
                email = email,
                password = password
            )
        )

        val errorConverter: Converter<ResponseBody, RegistrationResponseDTO> =
            retrofit.responseBodyConverter(RegistrationResponseDTO::class.java, arrayOf())

        val dto: RegistrationResponseDTO = when {
            response.isSuccessful -> {
                response.body()!!
            }

            else -> {
                response.errorBody()?.let { errBody ->
                    try {
                        errorConverter.convert(errBody)
                    } catch (_: Exception) {
                        RegistrationResponseDTO(
                            success = false,
                            message = "Server error ${response.code()}",
                        )
                    }
                } ?: RegistrationResponseDTO(
                    success = false,
                    message = "Unknown error ${response.code()}",
                )
            }
        }


        return dto.toDomain()
    }
}