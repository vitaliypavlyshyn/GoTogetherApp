package com.example.gotogether.data.registration

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApiService {
    @POST("/register")
    suspend fun register(@Body request: RegistrationDTO): Response<RegistrationResponseDTO>
}