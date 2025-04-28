package com.example.gotogether.data.registration

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApiService {
    @POST("/register")
    suspend fun register(@Body request: RegistrationRequest): ResponseDTO
}