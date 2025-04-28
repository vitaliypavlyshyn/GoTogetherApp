package com.example.gotogether.data.registration.repository

import com.example.gotogether.data.registration.RegistrationResponse
import com.example.gotogether.data.trip_request.dto.ResponseDTO
import com.example.gotogether.domain.login.Login
import com.example.gotogether.domain.registration.Registration

interface RegistrationRepository {
    suspend fun register(email: String,
                         password: String,
                         firstName: String,
                         lastName: String,
                         dateOfBirth: String
    ): Result<ResponseDTO>
}