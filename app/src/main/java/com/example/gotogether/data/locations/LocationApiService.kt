package com.example.gotogether.data.locations

import com.example.gotogether.data.user.UserDTO
import retrofit2.http.GET

interface LocationApiService {

    @GET("/locations")
    suspend fun getLocations(): List<LocationDTO>
}