package com.example.gotogether.data.registration.repository

import com.example.gotogether.data.registration.RegistrationApiService
import com.example.gotogether.data.registration.RegistrationRequest
import com.example.gotogether.data.registration.RegistrationResponse
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationApiService,
) : RegistrationRepository {
    override suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
    ): Result<ResponseDTO> {
        return try {
            val response = api.register(
                RegistrationRequest(
                    firstName = firstName,
                    lastName = lastName,
                    dateOfBirth = dateOfBirth,
                    email = email,
                    password = password
                )
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}