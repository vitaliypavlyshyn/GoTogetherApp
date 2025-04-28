package com.example.gotogether.data.car

import retrofit2.http.GET

interface CarApiService {
    @GET("/cars")
    suspend fun getCars(): List<CarResponse>
}