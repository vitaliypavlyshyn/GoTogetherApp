package com.example.gotogether.data.user

import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("/users/{userUuid}")
    suspend fun getUserByUuid(@Path("userUuid") userUuid: String): UserDTO
}