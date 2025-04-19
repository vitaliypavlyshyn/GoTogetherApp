package com.example.gotogether.data.locations.repository

import com.example.gotogether.domain.location.Location
import com.example.gotogether.domain.user.User

interface LocationRepository {
    suspend fun getLocations(): Result<List<Location>>
}