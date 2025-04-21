package com.example.gotogether.data.car

import com.example.gotogether.data.user.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CarApiService {
    @GET("/cars")
    suspend fun getCars(): List<CarDTO>
}