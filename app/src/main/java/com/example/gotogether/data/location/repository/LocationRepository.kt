package com.example.gotogether.data.location.repository

import com.example.gotogether.domain.location.Location

interface LocationRepository {
    suspend fun getLocations(): Result<List<Location>>
}