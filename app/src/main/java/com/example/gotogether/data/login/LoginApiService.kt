package com.example.gotogether.data.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequestDTO): Response<LoginResponseDTO>
}