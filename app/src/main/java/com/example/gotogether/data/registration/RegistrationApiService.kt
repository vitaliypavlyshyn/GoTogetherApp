package com.example.gotogether.data.registration

import com.example.gotogether.data.login.LoginRequestDTO
import com.example.gotogether.data.login.LoginResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApiService {
    @POST("/register")
    suspend fun register(@Body request: RegistrationRequestDTO): Response<RegistrationResponseDTO>
}