package com.example.gotogether.data.user

import com.example.gotogether.data.trip_request.dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {
    @GET("/users/{userUuid}")
    suspend fun getUserByUuid(@Path("userUuid") userUuid: String): UserResponse

    @GET("/me")
    suspend fun getCurrentUser(): UserResponse

    @PUT("users/{userUuid}")
    suspend fun updateUser(
        @Path("userUuid") userUuid: String,
        @Body request: UpdateUserRequest
    ): ResponseDTO

    @POST("user/{userUuid}")
    suspend fun deleteUser(@Path("userUuid") userUuid: String): ResponseDTO
}