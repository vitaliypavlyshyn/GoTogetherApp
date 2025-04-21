package com.example.gotogether.data.user

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {
    @GET("/users/{userUuid}")
    suspend fun getUserByUuid(@Path("userUuid") userUuid: String): UserDTO

    @GET("/me")
    suspend fun getCurrentUser(): UserDTO

    @PUT("users/{userUuid}")
    suspend fun updateUser(
        @Path("userUuid") userUuid: String,
        @Body request: UpdateUserRequestDTO
    ): Response<String>
}