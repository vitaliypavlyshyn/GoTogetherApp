package com.example.gotogether.data.locations.repository

import com.example.gotogether.data.locations.LocationApiService
import com.example.gotogether.data.locations.toDomainList
import com.example.gotogether.data.user.toDomain
import com.example.gotogether.domain.location.Location
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: LocationApiService
) : LocationRepository{
    override suspend fun getLocations(): Result<List<Location>> {
        return try {
            val locations = api.getLocations()
            Result.success(locations.toDomainList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}