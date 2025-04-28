package com.example.gotogether.data.location

import retrofit2.http.GET

interface LocationApiService {

    @GET("/locations")
    suspend fun getLocations(): List<LocationResponse>
}